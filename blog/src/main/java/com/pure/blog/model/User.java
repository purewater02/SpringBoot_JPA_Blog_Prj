package com.pure.blog.model;

import java.sql.Timestamp;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import org.hibernate.annotations.ColumnDefault;
import org.hibernate.annotations.CreationTimestamp;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity //User 클래스가 MySQL에 자동으로 테이블로 생성됨.
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class User {
	@Id 	//PK
	@GeneratedValue(strategy = GenerationType.IDENTITY) //프로젝트에서 연결된 DB의 넘버링 전략을 따라간다.
	private int id; //시퀀스, auto_increment
	
	@Column(nullable = false, length = 30)
	private String username; //아이디
	
	@Column(nullable = false, length = 100) //추후 해쉬(비밀번호 암호화)를 위함.
	private String password;
	
	@Column(nullable = false, length = 50)
	private String email;
	
	@ColumnDefault("'user'") //user는 작은 따옴표로 묶어야 문자임을 알려줄 수 있음.
	private String role; //admin, user, manager 등을 설정. //Enum을 쓰는 게 좋다. 도메인을 설정해서 이상한 값이 들어가는 것을 방지하기 위함.
	
	@CreationTimestamp //시간이 자동으로 입력됨
	private Timestamp createDate;
}
