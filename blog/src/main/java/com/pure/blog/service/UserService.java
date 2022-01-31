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
	
	@Transactional(readOnly = true)
	public User 회원찾기(String username) {
		User user = userRepository.findByUsername(username).orElseGet(() -> {
			return new User();
		})	;	
		return user;
	}
	
	@Transactional //내부 기능이 모두 정상작동 하면 commit, 하나라도 실패하면 rollback
	public int  회원가입(User user) {		
			try {
				userRepository.findByUsername(user.getUsername()).get(); //가입한 적이 없어서 username이 매칭되지 않으면 exception 발생
				return -1;				
			} catch (Exception e) {	//exception이 발생하면 정상적으로 회원가입 처리.
				String rawPassword = user.getPassword();
				String encPassword = encoder.encode(rawPassword);
				user.setPassword(encPassword);
				user.setRole(RoleType.USER);
				userRepository.save(user);
				return 1;
			}
	}

	@Transactional
	public void 회원정보수정(User user) {
		// 수정시에는 User 오브젝트를 영속화시켜서 영속화된 User 오브젝트를 수정해야 함.
		// 따라서 select문을 통해 User 오브젝트를 영속화시켜야 함.
		// 영속화된 오브젝트를 수정하면 자동으로 DB에 업데이트 되는 특성을 이용하여 회원정보수정.
		User persistance = userRepository.findById(user.getId())
				.orElseThrow(() -> {
					return new IllegalArgumentException("회원 찾기 실패");
				});
		
		//카카오 사용자가 아닐경우에만 수정 가능.
		if(persistance.getOauth() == null || persistance.getOauth().equals("")) {
			String rawPassword = user.getPassword();
			String encPassword = encoder.encode(rawPassword);
			persistance.setPassword(encPassword);
			persistance.setEmail(user.getEmail());
		}	


		// 회원수정 함수 종료 == 서비스 종료 == 트랜잭션 종료 --> 자동 커밋 (더티체킹)
	}

}
