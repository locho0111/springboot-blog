package com.springboot.blog.springbootblog.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.springboot.blog.springbootblog.entity.Category;

public interface CategoryRepository extends JpaRepository<Category, Long> {

}
