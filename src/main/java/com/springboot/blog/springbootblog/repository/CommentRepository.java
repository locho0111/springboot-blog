package com.springboot.blog.springbootblog.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.springboot.blog.springbootblog.entity.Comment;

public interface CommentRepository extends JpaRepository<Comment, Long> {
    ///
    List<Comment> findByPostId(long postId);
}
