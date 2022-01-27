package com.pure.blog.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.pure.blog.model.Board;

public interface BoardRepository extends JpaRepository<Board, Integer> { //Board 테이블이 관리하는 repo, PK는 Integer

}
