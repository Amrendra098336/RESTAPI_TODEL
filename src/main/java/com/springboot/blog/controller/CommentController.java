package com.springboot.blog.controller;

import com.springboot.blog.payload.CommentDto;
import com.springboot.blog.service.CommentService;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/")
@AllArgsConstructor
public class CommentController {

    private CommentService commentService;



    @PostMapping("/posts/{postid}/comments")
    public ResponseEntity<CommentDto> createComment(@PathVariable(value = "postid") Long postid,
                                                    @Valid @RequestBody CommentDto commentDto) {
        return new ResponseEntity<>(commentService.createComment(postid, commentDto), HttpStatus.CREATED);
    }

    @GetMapping("/posts/{postid}/comments")
    public List<CommentDto> getCommentsByPostId(@PathVariable(value = "postid") Long postId){
        return commentService.getCommentsByPostId(postId);
    }

    @GetMapping("/posts/{postid}/comments/{commentId}")
    public ResponseEntity<CommentDto> getCommentByID(@PathVariable(value = "postid" ) Long postId,
                                                     @PathVariable(value = "commentId") Long commentId){

        CommentDto commentsById = commentService.getCommentsById(postId, commentId);
        return new ResponseEntity<>(commentsById,HttpStatus.OK);
    }

    @PutMapping("/posts/{postid}/comments/{commentId}")
    public ResponseEntity<CommentDto> updateComment(@PathVariable(value = "postid" ) Long postId,
                                                    @PathVariable(value = "commentId") Long commentId,
                                                    @Valid @RequestBody CommentDto commentDto){
        CommentDto updateComment = commentService.updateComment(postId, commentId, commentDto);

        return new ResponseEntity<>(updateComment, HttpStatus.OK);

    }

    @DeleteMapping(("/posts/{postid}/comments/{commentId}"))
    public ResponseEntity<String> deleteComment(@PathVariable(value = "postid" ) Long postId,
                                                 @PathVariable(value = "commentId") Long commentId){
        commentService.deleteComment(postId, commentId);
        return new ResponseEntity<>("Comment Deleted Successfully", HttpStatus.OK);
    }
}
