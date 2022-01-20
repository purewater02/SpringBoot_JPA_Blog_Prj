package com.pure.blog.test;


import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;


//스프링이 com.pure.blog 패키지 이하를 스캔해서 모든 파일을 메모리에 new 하는 것은 아님.
//특정 어노테이션이 붙어있는 클래스 파일들을 new 해서 (이것이 IoC) 스프링 컨테이너에서 관리함.
@RestController
public class BlogControllerTest {
	
	//http://localhost:8282/test/hello
	@GetMapping("/test/hello")
	public String Hello() {
		return "<h1>Hello spring boot</h1>";
	}
}
