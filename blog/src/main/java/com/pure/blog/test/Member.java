package com.pure.blog.test;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

//DB에서 가지고 온 수정되지 않을 값이기 때문에 final을 붙여 쓰기도 한다.
@Data //Getter, Setter 모두 만들기
@NoArgsConstructor
public class Member {
	private  int id;
	private  String username;
	private  String password;
	private  String email;
	
	@Builder
	public Member(int id, String username, String password, String email) {
		super();
		this.id = id;
		this.username = username;
		this.password = password;
		this.email = email;
	}

	
	
}
