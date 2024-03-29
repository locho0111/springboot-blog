package com.springboot.blog.springbootblog.payload;

import java.util.List;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
@Schema(description = "PostDto Model Information")
public class PostDto {
    public PostDto() {
    }

    private Long id;

    @Schema(description = "Blog Post Title")
    @NotEmpty
    @Size(min = 2, message = "Post title should have at least 2 characters")
    private String title;

    @Schema(description = "Blog Post Description")
    @NotEmpty
    @Size(min = 10, message = "Post description should have at least 10 chracters")
    private String description;

    @Schema(description = "Blog Post Content")
    @NotEmpty
    private String content;

    @Schema(description = "Bloc Post Category")
    private Long categoryId;

    private List<CommentDto> comments;

}
