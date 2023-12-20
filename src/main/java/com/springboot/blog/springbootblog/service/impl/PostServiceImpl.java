package com.springboot.blog.springbootblog.service.impl;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.springboot.blog.springbootblog.entity.Post;
import com.springboot.blog.springbootblog.payload.PostDto;
import com.springboot.blog.springbootblog.repository.PostRepository;
import com.springboot.blog.springbootblog.service.PostService;

@Service
public class PostServiceImpl implements PostService {

    private PostRepository postRepository;

    public PostServiceImpl(PostRepository postRepository) {
        this.postRepository = postRepository;
    }

    @Override
    public PostDto createPost(PostDto postDto) {
        // convert DTO to entity
        Post post = new Post();
        post.setTitle(postDto.getTitle());
        post.setDescription(postDto.getDescription());
        post.setContent(postDto.getContent());

        Post newPost = postRepository.save(post);

        // convert enitty to DTO
        PostDto postResponse = new PostDto();
        postResponse.setId(newPost.getId());
        postResponse.setTitle(newPost.getTitle());
        postResponse.setDescription(newPost.getDescription());
        postResponse.setContent(newPost.getContent());

        return postResponse;
    }

}
