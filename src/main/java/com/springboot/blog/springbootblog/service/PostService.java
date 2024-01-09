package com.springboot.blog.springbootblog.service;

import java.util.List;

import com.springboot.blog.springbootblog.payload.PostDto;
import com.springboot.blog.springbootblog.payload.PostResponse;

public interface PostService {
    PostDto createPost(PostDto postDto);

    PostResponse getAllPosts(int pageNo, int pageSize, String sortBy, String sortDir);

    PostDto getPostById(Long id);

    PostDto updatePost(PostDto postDto, long id);

    void deletePost(long id);

    List<PostDto> getPostsByCategory(Long categoryId);
}
