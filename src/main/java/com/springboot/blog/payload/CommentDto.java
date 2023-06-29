package com.springboot.blog.payload;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import static com.springboot.blog.utils.AppConstants.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class CommentDto  {

    private Long id;

    @NotEmpty(message = NAME_NOT_BLANK)
    private String name;

    @Email(message = VALID_EMAIL_ERROR)
    @NotEmpty(message = EMAIL_NOT_BLANK)
    private String email;

    @NotEmpty(message = COMMENT_BODY_NOT_BLANK)
    @Size(min = 10, message = COMMENT_BODY_SIZE_ERROR)
    private String body;
}
