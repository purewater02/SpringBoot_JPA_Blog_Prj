package com.pure.blog.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.pure.blog.model.RoleType;
import com.pure.blog.model.User;
import com.pure.blog.repository.UserRepository;

//component-scan으로 Bean에 등록. (IoC)
@Service
public class UserService {

	@Autowired
	private UserRepository userRepository;
		
	@Autowired
	private BCryptPasswordEncoder encoder;
	
	@Transactional //내부 기능이 모두 정상작동 하면 commit, 하나라도 실패하면 rollback
	public int  회원가입(User user) {
		try {
			String rawPassword = user.getPassword();
			String encPassword = encoder.encode(rawPassword);
			user.setPassword(encPassword);
			user.setRole(RoleType.USER);
			userRepository.save(user);
			return 1;
		} catch (Exception e) {
			e.printStackTrace();
			System.out.println("UserService: 회원가입() : " + e.getMessage());
		}
		return -1;
	}

}
