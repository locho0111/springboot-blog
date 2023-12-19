package com.springboot.blog.springbootblog.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.springboot.blog.springbootblog.entity.Post;

public interface PostRepository extends JpaRepository<Post, Long> {

}
