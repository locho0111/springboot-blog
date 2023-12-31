package com.springboot.blog.springbootblog.service.impl;

import java.util.List;

import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import com.springboot.blog.springbootblog.entity.Category;
import com.springboot.blog.springbootblog.entity.Post;
import com.springboot.blog.springbootblog.exception.ResourceNotFoundException;
import com.springboot.blog.springbootblog.payload.PostDto;
import com.springboot.blog.springbootblog.payload.PostResponse;
import com.springboot.blog.springbootblog.repository.CategoryRepository;
import com.springboot.blog.springbootblog.repository.PostRepository;
import com.springboot.blog.springbootblog.service.PostService;

@Service
public class PostServiceImpl implements PostService {

    private PostRepository postRepository;
    private ModelMapper mapper;
    private CategoryRepository categoryRepository;

    public PostServiceImpl(PostRepository postRepository, ModelMapper mapper, CategoryRepository categoryRepository) {
        this.postRepository = postRepository;
        this.mapper = mapper;
        this.categoryRepository = categoryRepository;
    }

    @Override
    public PostDto createPost(PostDto postDto) {

        Category category = categoryRepository.findById(postDto.getCategoryId())
                .orElseThrow(() -> new ResourceNotFoundException("Category", "id", postDto.getCategoryId()));

        // convert DTto entity
        Post post = mapToEntity(postDto);
        post.setCategory(category);
        Post newPost = postRepository.save(post);

        // convert enitty to DTO
        PostDto postResponse = mapToDto(newPost);
        return postResponse;
    }

    @Override
    public PostResponse getAllPosts(int pageNo, int pageSize, String sortBy, String sortDir) {

        // List<PostDto> allPostDtos = new ArrayList<>();

        // for (Post post : allPosts) {
        // allPostDtos.add(mapToDto(post));
        // }
        // return allPostDtos;
        Sort sort = sortDir.equalsIgnoreCase(Sort.Direction.ASC.name()) ? Sort.by(sortBy).ascending()
                : Sort.by(sortBy).descending();
        // create Pageable intance
        Pageable pageable = PageRequest.of(pageNo, pageSize, sort);
        Page<Post> allPosts = postRepository.findAll(pageable);

        // get content for page object
        List<Post> listOfPosts = allPosts.getContent();

        List<PostDto> content = listOfPosts.stream().map(post -> mapToDto(post)).toList();

        PostResponse postResponse = new PostResponse();
        postResponse.setContent(content);
        postResponse.setPageNo(allPosts.getNumber());
        postResponse.setPageSize(allPosts.getSize());
        postResponse.setTotalElements(allPosts.getTotalElements());
        postResponse.setTotalPages(allPosts.getTotalPages());
        postResponse.setLast(allPosts.isLast());

        return postResponse;

    }

    private PostDto mapToDto(Post post) {
        // PostDto postDto = new PostDto();
        // postDto.setId(post.getId());
        // postDto.setTitle(post.getTitle());
        // postDto.setDescription(post.getDescription());
        // postDto.setContent(post.getContent());
        PostDto postDto = mapper.map(post, PostDto.class);
        return postDto;
    }

    private Post mapToEntity(PostDto postDto) {
        Post post = mapper.map(postDto, Post.class);
        // Post post = new Post();
        // post.setTitle(postDto.getTitle());
        // post.setDescription(postDto.getDescription());
        // post.setContent(postDto.getContent());
        return post;
    }

    @Override
    public PostDto getPostById(Long id) {
        Post post = postRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Post", "id", id));
        return mapToDto(post);
    }

    @Override
    public PostDto updatePost(PostDto postDto, long id) {
        Post post = postRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Post", "id", id));

        if (postDto.getTitle() != null && !postDto.getTitle().isEmpty()) {
            post.setTitle(postDto.getTitle());
        }
        if (postDto.getDescription() != null && !postDto.getDescription().isEmpty()) {
            post.setDescription(postDto.getDescription());
        }

        if (postDto.getContent() != null && !postDto.getContent().isEmpty()) {
            post.setContent(postDto.getContent());
        }
        if (postDto.getCategoryId() != null && postDto.getCategoryId() != post.getCategory().getId()) {
            Category newCategory = categoryRepository.findById(postDto.getCategoryId())
                    .orElseThrow(() -> new ResourceNotFoundException("Category", "id", postDto.getCategoryId()));
            post.setCategory(newCategory);
        }

        Post updatedPost = postRepository.save(post);
        return mapToDto(updatedPost);
    }

    @Override
    public void deletePost(long id) {
        Post post = postRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Post", "id", id));
        postRepository.delete(post);
    }

    @Override
    public List<PostDto> getPostsByCategory(Long categoryId) {
        categoryRepository.findById(categoryId)
                .orElseThrow(() -> new ResourceNotFoundException("Category", "id", categoryId));

        List<Post> posts = postRepository.findBycategoryId(categoryId);

        return posts.stream().map((post) -> mapToDto(post)).toList();
    }

}
