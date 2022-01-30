package com.pure.blog.model;

import java.sql.Timestamp;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import org.hibernate.annotations.ColumnDefault;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.DynamicInsert;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

//@DynamicInsert //null인 필드는 제외하고 insert 해줌.
@Entity //User 클래스가 MySQL에 자동으로 테이블로 생성됨.
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class User {
	@Id 	//PK
	@GeneratedValue(strategy = GenerationType.IDENTITY) //프로젝트에서 연결된 DB의 넘버링 전략을 따라간다.
	private int id; //시퀀스, auto_increment
	
	@Column(nullable = false, length = 30, unique = true)
	private String username; //아이디
	
	@Column(nullable = false, length = 100) //추후 해쉬(비밀번호 암호화)를 위함.
	private String password;
	
	@Column(nullable = false, length = 50)
	private String email;
	
	//@ColumnDefault("user")
	//DB는 RoleType이라는 타입이 없음.
	@Enumerated(EnumType.STRING)
	private RoleType role; // USER, ADMIN 두개만 들어갈 수 있도록 설정.
	
	private String oauth; //kakao, google 등
	
	@CreationTimestamp //시간이 자동으로 입력됨
	private Timestamp createDate;
}
