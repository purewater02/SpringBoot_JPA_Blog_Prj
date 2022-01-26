package com.pure.blog.config.auth;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.pure.blog.model.User;
import com.pure.blog.repository.UserRepository;

@Service
public class PrincipalDetailService implements UserDetailsService{
	
	@Autowired
	private UserRepository userRepository;
	
	//스프링이 로그인 요청을 처리할 때 password는 알아서 처리해줌. 그래서 username만 DB에 있는지 확인시켜 주면 됨.
	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		User principal = userRepository.findByUsername(username).orElseThrow( () -> {
			return new UsernameNotFoundException("해당 사용자를 찾을 수 없습니다. :" + username);
		});
		return new PrincipalDetail(principal); //시큐리티의 세션에 유저 정보가 저장됨.
	}
}
