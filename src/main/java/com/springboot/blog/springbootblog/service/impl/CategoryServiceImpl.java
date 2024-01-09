package com.springboot.blog.springbootblog.service.impl;

import java.util.List;

import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import com.springboot.blog.springbootblog.entity.Category;
import com.springboot.blog.springbootblog.exception.ResourceNotFoundException;
import com.springboot.blog.springbootblog.payload.CategoryDto;
import com.springboot.blog.springbootblog.repository.CategoryRepository;
import com.springboot.blog.springbootblog.service.CatergoryService;

@Service
public class CategoryServiceImpl implements CatergoryService {
    private CategoryRepository categoryRepository;
    private ModelMapper modelMapper;

    public CategoryServiceImpl(CategoryRepository categoryRepository, ModelMapper modelMapper) {
        this.categoryRepository = categoryRepository;
        this.modelMapper = modelMapper;
    }

    @Override
    public CategoryDto addCategory(CategoryDto categoryDto) {
        Category category = mapToEntity(categoryDto);

        Category category_response = categoryRepository.save(category);

        return mapToDto(category_response);
    }

    private CategoryDto mapToDto(Category category) {
        CategoryDto categoryDto = modelMapper.map(category, CategoryDto.class);
        return categoryDto;
    }

    private Category mapToEntity(CategoryDto categoryDto) {
        Category category = modelMapper.map(categoryDto, Category.class);
        return category;
    }

    @Override
    public CategoryDto getCategory(Long categoryId) {
        Category category = categoryRepository.findById(categoryId)
                .orElseThrow(() -> new ResourceNotFoundException("category", "id", categoryId));

        return mapToDto(category);
    }

    @Override
    public List<CategoryDto> getAllCategory() {
        List<Category> categories = categoryRepository.findAll();

        List<CategoryDto> categoriesDtos = categories.stream().map(category -> mapToDto(category)).toList();

        return categoriesDtos;
    }

    @Override
    public CategoryDto updateCategory(CategoryDto categoryDto, Long categoryId) {
        Category category = categoryRepository.findById(categoryId)
                .orElseThrow(() -> new ResourceNotFoundException("category", "id", categoryId));

        if (categoryDto.getName() != null && !categoryDto.getName().isEmpty()) {
            category.setName(categoryDto.getName());
        }

        if (categoryDto.getDescription() != null && !categoryDto.getDescription().isEmpty()) {
            category.setDescription(categoryDto.getDescription());
        }

        return mapToDto(categoryRepository.save(category));
    }

    @Override
    public void deleteCategory(Long categoryId) {
        Category category = categoryRepository.findById(categoryId)
                .orElseThrow(() -> new ResourceNotFoundException("category", "id", categoryId));

        categoryRepository.delete(category);
    }

}
