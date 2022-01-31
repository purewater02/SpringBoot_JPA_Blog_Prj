package com.pure.blog.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.pure.blog.model.Reply;

public interface ReplyRepository extends JpaRepository<Reply, Integer> {

}
