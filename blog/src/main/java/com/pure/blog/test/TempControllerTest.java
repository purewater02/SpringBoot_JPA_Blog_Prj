package com.pure.blog.test;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class TempControllerTest {
	
	@GetMapping("/temp/home")
	public String tempHome() {
		//yml에 mvc 설정이 없는 경우.
		//파일리턴 기본경로: src/main/resources/static
		//리턴명: /home.html
		// 전체경로: src/main/resources/static/home/html
		return "/home.html";
	}
	
	@GetMapping("/temp/img")
	public String tempImg() {
		return "/discord.gif";
	}
	
	@GetMapping("/temp/jsp")
	public String tempJsp() {
		//prefix: /WEB-INF/views/
		//suffix: .jsp
		return "test"; // 전체경로: /WEB-INF/views/test.jsp
	}
}
