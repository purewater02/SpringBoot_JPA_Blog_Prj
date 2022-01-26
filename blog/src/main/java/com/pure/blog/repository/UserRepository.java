package com.pure.blog.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.pure.blog.model.User;

//DAO와 같음
//자동으로 Bean 등록이 되기 때문에 @Repository 생략이 가능.
public interface UserRepository extends JpaRepository<User, Integer> { //User 테이블이 관리하는 repo, PK는 Integer
	// select * from user where username = 1?;
	Optional<User> findByUsername(String username);
}




// JPA Naming 쿼리
// select * from user where username = ? and password = ?; 
// ?에는 각각의 파라미터가 들어감.
//User findByUsernameAndPassword(String username, String password);

/*
 * //Native Query를 쓰는 방식
 * 
 * @Query(value="select * from user where username = ?1 and password = ?2",
 * nativeQuery = true) User login(String username, String password);
 */