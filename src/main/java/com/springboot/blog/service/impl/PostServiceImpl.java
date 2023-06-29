package com.springboot.blog.service.impl;

import com.springboot.blog.entity.Post;
import com.springboot.blog.exception.ResourceNotFoundException;
import com.springboot.blog.payload.PostDto;
import com.springboot.blog.payload.PostResponse;
import com.springboot.blog.repository.PostRepository;
import com.springboot.blog.service.PostService;
import lombok.AllArgsConstructor;
import org.aspectj.asm.IModelFilter;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Service
@AllArgsConstructor
public class PostServiceImpl implements PostService {

    private PostRepository postRepository;

    private ModelMapper modelMapper;

    @Override
    public PostDto createPost(PostDto postDto) {
        Post post = mapDtoToEntity(postDto);
        Post savedPost = postRepository.save(post);
        return mapPostToDto(savedPost);
    }


    @Override
    public PostResponse getAllPosts(int pageNo, int pageSize, String sortBy, String sortDir) {

        Sort sort = sortDir.equalsIgnoreCase(Sort.Direction.ASC.name()) ? Sort.by(sortBy).ascending()
                    : Sort.by(sortBy).descending();

        //Create Pagable Instance
        Pageable pageable = PageRequest.of(pageNo, pageSize, sort);
        Page<Post> returnedPosts = postRepository.findAll(pageable);
        //Get content from page Object
        List<Post> postList = returnedPosts.getContent() ;

        Stream<PostDto> postDtoList = postList.stream().map(this::mapPostToDto);
        List<PostDto> content = postDtoList.collect(Collectors.toList());
        PostResponse postResponse = new PostResponse();
        postResponse.setContent(content);
        postResponse.setPageNo(returnedPosts.getNumber());
        postResponse.setPageSize(returnedPosts.getSize());
        postResponse.setTotalElement(returnedPosts.getTotalElements());
        postResponse.setTotalPages(returnedPosts.getTotalPages());
        postResponse.setLast(returnedPosts.isLast() );
        return postResponse;
    }

    @Override
    public PostDto getPostById(Long id) {
        Post post = postRepository.findById(id).orElseThrow(()->new ResourceNotFoundException("POST", "id", id));
        return mapPostToDto(post);
    }

    @Override
    public PostDto updatePost(PostDto postDto, Long id) {
        Post post = postRepository.findById(id).orElseThrow(()->new ResourceNotFoundException("POST", "id", id));

        post.setTitle(postDto.getTitle());
        post.setDescription(postDto.getDescription());
        post.setContent(postDto.getContent());

        Post updatedPost = postRepository.save(post);
        return mapPostToDto(updatedPost);
    }

    @Override
    public void deletePost(Long id) {
        Post post = postRepository.findById(id).orElseThrow(()->new ResourceNotFoundException("POST", "id", id));
        postRepository.delete(post);
    }


    private PostDto mapPostToDto(Post post) {

        PostDto postDto = modelMapper.map(post, PostDto.class);

       /* PostDto postDto = new PostDto();
        postDto.setId(post.getId());
        postDto.setTitle(post.getTitle());
        postDto.setContent(post.getContent());
        postDto.setDescription(post.getDescription());

        */

        return postDto;
    }


    private  Post mapDtoToEntity(PostDto postDto) {
        Post post = modelMapper.map(postDto, Post.class);
        /*
        post.setTitle(postDto.getTitle());
        post.setContent(postDto.getContent());
        post.setDescription(postDto.getDescription());
         */
        return post;
    }

}
