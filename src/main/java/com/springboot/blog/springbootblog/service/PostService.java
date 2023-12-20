package com.springboot.blog.springbootblog.service;

import com.springboot.blog.springbootblog.payload.PostDto;

public interface PostService {
    PostDto createPost(PostDto postDto);
}
