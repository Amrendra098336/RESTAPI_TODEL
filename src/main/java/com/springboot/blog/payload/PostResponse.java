package com.springboot.blog.payload;

import lombok.*;

import java.util.List;


@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class PostResponse {

    private List<PostDto> content;
    private Integer pageNo;
    private Integer pageSize;
    private Long totalElement;
    private Integer totalPages;
    private Boolean last;

}
