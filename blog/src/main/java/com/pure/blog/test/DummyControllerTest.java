package com.pure.blog.test;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.pure.blog.model.RoleType;
import com.pure.blog.model.User;
import com.pure.blog.repository.UserRepository;

@RestController
public class DummyControllerTest {
	
	@Autowired
	private UserRepository userRepository;
	
	@DeleteMapping("/dummy/user/{id}")
	public String delete(@PathVariable int id) {
		try {
			userRepository.deleteById(id);
		} catch(EmptyResultDataAccessException e) {
			return "삭제에 실패하였습니다. 해당 id는 DB에 없습니다.";
		}
		return "삭제되었습니다. id: " + id;				
	}
	
	
	//email, password 수정
	@Transactional //함수 종료시 자동 commit
	@PutMapping("/dummy/user/{id}")
	public User updateUser(@PathVariable int id, @RequestBody User requestUser) {//MessageConverter의 Jackson라이브러리가 알아서 파싱해줌.
		System.out.println("id: "+ id);
		System.out.println("password: " + requestUser.getPassword());
		System.out.println("email: "+ requestUser.getEmail());
		
		//이미 정보가 다 들어가 있는 녀석을 다시 select해서 가져와야 null이 들어가는 것을 피할 수 있다.
		User user = userRepository.findById(id).orElseThrow(() -> {
			return new IllegalArgumentException("수정에 실패했습니다...");
		});
		user.setPassword(requestUser.getPassword());
		user.setEmail(requestUser.getEmail());
		
		//userRepository.save(user); //save 메서드는 id를 전달하지 않으면 insert, id를 전달할 시에는 id에 해당하는 데이터가 있으면 update, 없으면 insert.
		
		//transactional --> 더티 체킹
		
		return user;
	}
	
	
	@GetMapping("/dummy/users")
	public List<User> list() {
		return userRepository.findAll();
	}
	
	//한 페이지당 2건에 데이터를 리턴받기 (size=2)
	@GetMapping("/dummy/user")
	public List<User> pageList(@PageableDefault(size=2, sort="id", direction = Sort.Direction.DESC) Pageable pageable) {
		Page<User> pagingUser = userRepository.findAll(pageable);
		List<User> users = pagingUser.getContent();
		return users;
	}
	
	
	// {id} 주소로 파라미터를 전달 받을 수 있음.
	@GetMapping("/dummy/user/{id}")
	public User detail(@PathVariable int id) {
		//{id}로 DB에서 select시 없는 id를 찾으면 null이 리턴될 수 있음.
		//Optional로 User를 처리하여 null인 경우를 배제할 수 있음.
		User user = userRepository.findById(id).orElseThrow(() -> {
				return new IllegalArgumentException("해당 유저는 없습니다. id: " + id);			
		});
		return user;
	}
	
	
	@PostMapping("/dummy/join")
	public String join(User user) { // key = value 규칙
		System.out.println("username: " + user.getUsername());
		System.out.println("password: " + user.getPassword());
		System.out.println("email: " + user.getEmail());
		
		user.setRole(RoleType.USER);		
		userRepository.save(user); //DB에 insert 됨.
		return "회원가입이 완료되었습니다.";
	}
}
