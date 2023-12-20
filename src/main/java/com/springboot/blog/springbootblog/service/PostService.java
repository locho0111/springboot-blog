package com.springboot.blog.springbootblog.service;

import java.util.List;

import com.springboot.blog.springbootblog.payload.PostDto;

public interface PostService {
    PostDto createPost(PostDto postDto);

    List<PostDto> getAllPosts();
}
