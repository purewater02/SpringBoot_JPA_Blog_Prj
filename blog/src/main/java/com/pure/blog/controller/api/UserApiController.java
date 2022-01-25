package com.pure.blog.controller.api;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.pure.blog.dto.ResponseDto;
import com.pure.blog.model.RoleType;
import com.pure.blog.model.User;
import com.pure.blog.service.UserService;

@RestController
public class UserApiController {

	@Autowired
	private UserService userService;
	
	@PostMapping("/api/user")
	public ResponseDto<Integer> save(@RequestBody User user) {
		System.out.println("UserApiController: save 호출됨");
		//DB에 insert 하기
		user.setRole(RoleType.USER);
		int result = userService.회원가입(user);		
		return new ResponseDto<Integer>(HttpStatus.OK.value(), result); //(status, data)
	}
}
