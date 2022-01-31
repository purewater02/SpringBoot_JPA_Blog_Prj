package com.pure.blog.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.pure.blog.model.Board;
import com.pure.blog.model.Reply;
import com.pure.blog.model.User;
import com.pure.blog.repository.BoardRepository;
import com.pure.blog.repository.ReplyRepository;



//component-scan으로 Bean에 등록. (IoC)
@Service
public class BoardService {

	@Autowired
	private BoardRepository boardRepository;
	
	@Autowired
	private ReplyRepository replyRepository;
	
	@Transactional //내부 기능이 모두 정상작동 하면 commit, 하나라도 실패하면 rollback
	public void  글쓰기(Board board, User user) {
		board.setCount(0);
		board.setUser(user);		
		boardRepository.save(board);
	}
	
	@Transactional(readOnly = true)
	public Page<Board> 글목록(Pageable pageable) {
		return boardRepository.findAll(pageable);
	}
	
	@Transactional(readOnly = true)
	public Board 글상세보기(int id) {
		return boardRepository.findById(id)
				.orElseThrow( () -> {
					return new IllegalArgumentException("글 상세보기 실패: id를 찾을 수 없습니다.");
				});
	}
	
	@Transactional
	public void 글삭제하기(int id) {
		System.out.println("글삭제 id: "+id);
		boardRepository.deleteById(id);			
	}

	@Transactional
	public void 글수정하기(int id, Board requestBoard) {
		Board board = boardRepository.findById(id)
				.orElseThrow( () -> {
					return new IllegalArgumentException("글 찾기 실패: id를 찾을 수 없습니다.");
				}); //영속화 완료		
		board.setTitle(requestBoard.getTitle());
		board.setContent(requestBoard.getContent());		
		//서비스 종료시 트랜잭션 종료 -> 더티체킹 -> DB flush로 자동 업데이트 됨.
	}

	@Transactional
	public void 댓글쓰기(User user, int boardId, Reply requestReply) {
		Board board = boardRepository.findById(boardId).orElseThrow(() -> {
			return new IllegalArgumentException("댓글 쓰기 실패: 게시글 아이디를 찾을 수 없습니다.");
		});
		requestReply.setUser(user);
		requestReply.setBoard(board);
		replyRepository.save(requestReply);
		
	}

}
