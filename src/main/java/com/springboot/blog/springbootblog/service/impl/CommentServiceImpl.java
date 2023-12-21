package com.springboot.blog.springbootblog.service.impl;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import com.springboot.blog.springbootblog.entity.Comment;
import com.springboot.blog.springbootblog.entity.Post;
import com.springboot.blog.springbootblog.exception.BlogAPIException;
import com.springboot.blog.springbootblog.exception.ResourceNotFoundException;
import com.springboot.blog.springbootblog.payload.CommentDto;
import com.springboot.blog.springbootblog.repository.CommentRepository;
import com.springboot.blog.springbootblog.repository.PostRepository;
import com.springboot.blog.springbootblog.service.CommentService;

@Service
public class CommentServiceImpl implements CommentService {
    private CommentRepository commentRepository;
    private PostRepository postRepository;

    public CommentServiceImpl(CommentRepository commentRepository, PostRepository postRepository) {
        this.commentRepository = commentRepository;
        this.postRepository = postRepository;
    }

    @Override
    public CommentDto createComment(long postId, CommentDto commentDto) {
        Comment comment = mapToEntity(commentDto);

        // retrive post entiry by id
        Post post = postRepository.findById(postId)
                .orElseThrow(() -> new ResourceNotFoundException("Post", "id", postId));

        comment.setPost(post);

        Comment savedComment = commentRepository.save(comment);

        return mapToDto(savedComment);

    }

    private Comment mapToEntity(CommentDto commentDto) {
        Comment comment = new Comment();

        comment.setId(commentDto.getId());
        comment.setName(commentDto.getName());
        comment.setEmail(commentDto.getEmail());
        comment.setBody(commentDto.getBody());

        return comment;
    }

    private CommentDto mapToDto(Comment comment) {
        CommentDto commentDto = new CommentDto(
                comment.getId(),
                comment.getName(),
                comment.getEmail(),
                comment.getBody());

        return commentDto;
    }

    @Override
    public List<CommentDto> getCommentByPostId(long postId) {
        // Post post = postRepository.findById(postId)
        // .orElseThrow(() -> new ResourceNotFoundException("Post", "id", postId));
        // System.out.println(post.getComments());

        List<Comment> comments = commentRepository.findByPostId(postId);
        return comments.stream().map(comment -> mapToDto(comment)).toList();
    }

    @Override
    public CommentDto getCommentById(long postId, long commentId) {

        Comment comment = checkValidComment(postId, commentId);
        return mapToDto(comment);
    }

    @Override
    public CommentDto updateCommentById(long postId, long commentId, CommentDto commentDto) {
        Comment comment = checkValidComment(postId, commentId);

        // update comment entity
        comment.setName(commentDto.getName());
        comment.setEmail(commentDto.getEmail());
        comment.setBody(commentDto.getBody());

        // saved comment entity
        Comment updatedComment = commentRepository.save(comment);
        // return updated entity
        return mapToDto(updatedComment);
    }

    @Override
    public void deleteCommentById(long postId, long commentId) {
        Comment comment = checkValidComment(postId, commentId);

        commentRepository.delete(comment);
    }

    private Comment checkValidComment(long postId, long commentId) {
        // retrieve post entity
        Post post = postRepository.findById(postId)
                .orElseThrow(() -> new ResourceNotFoundException("Post", "id", postId));

        // retrieve comment entity
        Comment comment = commentRepository.findById(commentId)
                .orElseThrow(() -> new ResourceNotFoundException("Comment", "id", commentId));

        // Check comment entity belong to post entity
        if (!comment.getPost().getId().equals(post.getId())) {
            throw new BlogAPIException(HttpStatus.BAD_REQUEST, "Comment does not belong to post.");
        }

        return comment;
    }
}
