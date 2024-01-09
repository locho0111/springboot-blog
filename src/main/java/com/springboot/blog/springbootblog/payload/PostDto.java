package com.springboot.blog.springbootblog.payload;

import java.util.List;

import com.springboot.blog.springbootblog.entity.Category;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class PostDto {
    public PostDto() {
    }

    private Long id;

    @NotEmpty
    @Size(min = 2, message = "Post title should have at least 2 characters")
    private String title;

    @NotEmpty
    @Size(min = 10, message = "Post description should have at least 10 chracters")
    private String description;

    @NotEmpty
    private String content;

    private Long categoryId;

    private List<CommentDto> comments;

}
