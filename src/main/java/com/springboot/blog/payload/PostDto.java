package com.springboot.blog.payload;


import com.springboot.blog.entity.Comment;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.*;

import java.util.Set;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class PostDto {
    private Long id;

    @NotEmpty
    @Size(min = 5, message = "Post Title must have at-least 5 character")
    private String title;

    @NotEmpty
    @Size(min = 20, message = "Post Description must have at-least 20 character")
    private String description;

    @NotEmpty
    private String content;
    private Set<CommentDto> comments;
}
