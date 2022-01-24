package com.pure.blog.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.pure.blog.model.User;

//DAO와 같음
//자동으로 Bean 등록이 되기 때문에 @Repository 생략이 가능.
public interface UserRepository extends JpaRepository<User, Integer> { //User 테이블이 관리하는 repo, PK는 Integer

}
