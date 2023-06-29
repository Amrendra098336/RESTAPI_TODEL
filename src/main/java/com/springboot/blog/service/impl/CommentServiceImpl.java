package com.springboot.blog.service.impl;

import com.springboot.blog.entity.Comment;
import com.springboot.blog.entity.Post;
import com.springboot.blog.exception.BlogApiException;
import com.springboot.blog.exception.ResourceNotFoundException;
import com.springboot.blog.payload.CommentDto;
import com.springboot.blog.repository.CommentRepository;
import com.springboot.blog.repository.PostRepository;
import com.springboot.blog.service.CommentService;
import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class CommentServiceImpl implements CommentService {

    private CommentRepository commentRepository;
    private PostRepository postRepository;

    private ModelMapper modelMapper;


    @Override
    public CommentDto createComment(Long postId, CommentDto commentDto) {

        Comment comment = mapDtoToEntity(commentDto);
        //retrive post by ID
        Post post = getPost(postId);
        comment.setPost(post);
        //save comment to DB
        Comment saveComment = commentRepository.save(comment);
        return mapCommentEntityToDto(saveComment);
    }

    @Override
    public List<CommentDto> getCommentsByPostId(Long postId) {
        List<Comment> comments = commentRepository.findByPostId(postId);

        return comments.stream().map(this::mapCommentEntityToDto).collect(Collectors.toList());

    }

    @Override
    public CommentDto getCommentsById(Long postId, Long commentId) {
        Post post = getPost(postId);

        Comment comment = getComment(commentId);

        if(!comment.getPost().getId().equals(post.getId())){
            throw new BlogApiException(HttpStatus.BAD_REQUEST, "Comment ID doesn't belong to the Post");
        }

        return mapCommentEntityToDto(comment);
    }

    @Override
    public CommentDto updateComment(Long postId, Long commentId, CommentDto commentRequest) {

        Post post = getPost(postId);

        Comment comment = getComment(commentId);

        if(!comment.getPost().getId().equals(post.getId())){
            throw new BlogApiException(HttpStatus.BAD_REQUEST, "Comment ID doesn't belong to the Post");
        }

        if(commentRequest.getEmail()!= null){
            comment.setEmail(commentRequest.getEmail());
        }
        if(commentRequest.getName() != null){
            comment.setName(commentRequest.getName());
        }
        if(commentRequest.getBody() != null){
            comment.setBody(commentRequest.getBody());
        }
        Comment updatedComment = commentRepository.save(comment);


        return mapCommentEntityToDto(updatedComment);
    }

    @Override
    public void deleteComment(Long postId, Long commentId) {
        Post post = getPost(postId);

        Comment comment = getComment(commentId);
        if(!comment.getPost().getId().equals(post.getId())){
            throw new BlogApiException(HttpStatus.BAD_REQUEST, "Comment ID doesn't belong to the Post");
        }

        commentRepository.delete(comment);

    }


    private Comment getComment(Long commentId) {
        Comment comment = commentRepository.findById(commentId).orElseThrow(
                () -> new ResourceNotFoundException("Comment", "id", commentId));
        return comment;
    }

    private Post getPost(Long postId) {
        Post post = postRepository.findById(postId).orElseThrow(
                () -> new ResourceNotFoundException("Post", "id", postId));
        return post;
    }


    private CommentDto mapCommentEntityToDto(Comment comment) {
        return modelMapper.map(comment, CommentDto.class);
        //new CommentDto(comment.getId(), comment.getName(), comment.getEmail(), comment.getBody());
    }

    private Comment mapDtoToEntity(CommentDto commentDto) {
        return modelMapper.map(commentDto, Comment.class);
        //new Comment(commentDto.getId(), commentDto.getName(), commentDto.getEmail(), commentDto.getBody());
    }
}
