package com.springboot.blog.springbootblog.payload;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class PostDto {
    public PostDto() {
    }

    private Long id;
    private String title;
    private String description;
    private String content;

}
