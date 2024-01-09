package com.springboot.blog.springbootblog.service;

import java.util.List;

import com.springboot.blog.springbootblog.payload.CategoryDto;

public interface CatergoryService {
    CategoryDto addCategory(CategoryDto categoryDto);

    CategoryDto getCategory(Long categoryId);

    List<CategoryDto> getAllCategory();

    CategoryDto updateCategory(CategoryDto categoryDto, Long id);

    void deleteCategory(Long id);
}
