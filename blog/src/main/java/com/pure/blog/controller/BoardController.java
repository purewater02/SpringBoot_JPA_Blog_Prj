package com.pure.blog.controller;

import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

import com.pure.blog.config.auth.PrincipalDetail;

@Controller
public class BoardController {

	@GetMapping({"","/"})
	public String index(@AuthenticationPrincipal PrincipalDetail principal) {
		System.out.println("로그인 아이디: "+principal.getUsername());
		return "index";
	}
}
