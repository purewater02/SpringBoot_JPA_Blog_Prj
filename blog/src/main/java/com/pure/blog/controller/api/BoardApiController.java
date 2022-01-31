package com.pure.blog.controller.api;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.pure.blog.config.auth.PrincipalDetail;
import com.pure.blog.dto.ResponseDto;
import com.pure.blog.model.Board;
import com.pure.blog.model.Reply;
import com.pure.blog.service.BoardService;

@RestController
public class BoardApiController {

	@Autowired
	private BoardService boardService;
	
	@PostMapping("/api/boardWrite")
	public ResponseDto<Integer> save(@RequestBody Board board, @AuthenticationPrincipal PrincipalDetail principal) {
		boardService.글쓰기(board, principal.getUser());
		return new ResponseDto<Integer>(HttpStatus.OK.value(), 1);
	}
	
	@DeleteMapping("/api/boardDelete/{id}")
	public ResponseDto<Integer> deleteById(@PathVariable int id) {
		boardService.글삭제하기(id);
		return new ResponseDto<Integer>(HttpStatus.OK.value(), 1);
	}
	
	@PutMapping("/api/boardUpdate/{id}")
	public ResponseDto<Integer> update(@PathVariable int id, @RequestBody Board board) {
		boardService.글수정하기(id, board);
		return new ResponseDto<Integer>(HttpStatus.OK.value(), 1);
	}
	
	@PostMapping("/api/board/{boardId}/replyWrite")
	public ResponseDto<Integer> replyWrite(@PathVariable int boardId, @RequestBody Reply reply, @AuthenticationPrincipal PrincipalDetail principal) {
		boardService.댓글쓰기(principal.getUser(), boardId, reply);
		return new ResponseDto<Integer>(HttpStatus.OK.value(), 1);
	}
}
