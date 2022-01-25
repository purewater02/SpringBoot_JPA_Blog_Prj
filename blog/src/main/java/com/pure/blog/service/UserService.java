package com.pure.blog.service;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.pure.blog.model.User;
import com.pure.blog.repository.UserRepository;

//component-scan으로 Bean에 등록. (IoC)
@Service
public class UserService {

	@Autowired
	private UserRepository userRepository;
	
	@Transactional //내부 기능이 모두 정상작동 하면 commit, 하나라도 실패하면 rollback
	public int  회원가입(User user) {
		try {
			userRepository.save(user);
			return 1;
		} catch (Exception e) {
			e.printStackTrace();
			System.out.println("UserService: 회원가입() : " + e.getMessage());
		}
		return -1;
	}
}
