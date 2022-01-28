package com.pure.blog.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

// 인증이 되지 않은 사용자들이 출입할 수 있는 /auth/** 경로 허용
// 주소가 "/"일 경우 index.jsp 허용
// static에 있는 /js/**, /css/**, /image/** 등등 허용.

@Controller
public class UserController {

	@GetMapping("/auth/joinForm")
	public String joinForm() {
		return "user/joinForm";
	}
	
	@GetMapping("/auth/loginForm")
	public String loginForm() {
		return "user/loginForm";
	}
	
	@GetMapping("/user/updateForm")
	public String updateForm() {
		return "user/updateForm";
	}
	
}
