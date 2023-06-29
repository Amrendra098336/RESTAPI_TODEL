package com.springboot.blog.payload;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;

import java.util.Date;

@AllArgsConstructor
@Getter
public class ErrorDetails  {

    private Date timeStamp;
    private String message;
    private String details;
    private HttpStatus errorCode;


}
