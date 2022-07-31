package com.blogapis.blogapi.controller;

import com.blogapis.blogapi.payload.BaseResponseDTO;
import com.blogapis.blogapi.payload.FileResponseDTO;
import com.blogapis.blogapi.payload.PostResponseDTO;
import com.blogapis.blogapi.payload.PostsDTO;
import com.blogapis.blogapi.service.FileService;
import com.blogapis.blogapi.service.PostsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.Valid;
import java.io.IOException;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@RestController
@RequestMapping("/api/v1")
public class PostsController {

    @Autowired
    private PostsService postsService;

    @Autowired
    private FileService fileService;

    @Value("${project.image}")
    private String path;

    @PostMapping("/user/{userId}/category/{categoryId}/post")
    public ResponseEntity<PostsDTO> createPost(
            @Valid
            @RequestBody PostsDTO postsDTO,
            @PathVariable Integer userId,
            @PathVariable Integer categoryId
    ) {
        PostsDTO createdPostsDTO = postsService.createPost(postsDTO, userId, categoryId);
        return new ResponseEntity<>(createdPostsDTO, HttpStatus.CREATED);
    }

    @GetMapping("/user/{userId}/posts")
    public ResponseEntity<List<PostsDTO>> getAllPostsByUser(@PathVariable Integer userId) {
        List<PostsDTO> postsDTOS = postsService.getPostsByUser(userId);
        return new ResponseEntity<List<PostsDTO>>(postsDTOS, HttpStatus.OK);
    }

    @GetMapping("/category/{categoryId}/posts")
    public ResponseEntity<List<PostsDTO>> getAllPostsByCategory(@PathVariable Integer categoryId) {
        List<PostsDTO> postsDTOS = postsService.getPostsByCategory(categoryId);
        return new ResponseEntity<List<PostsDTO>>(postsDTOS, HttpStatus.OK);
    }

    @GetMapping("/post/{postId}")
    public ResponseEntity<PostsDTO> getPostById(@PathVariable Integer postId) {
        PostsDTO postsDTO = postsService.getPost(postId);
        return new ResponseEntity<PostsDTO>(postsDTO, HttpStatus.OK);
    }

    @GetMapping("/posts")
    public ResponseEntity<PostResponseDTO> getAllPosts(
            @RequestParam(value = "pageNumber", defaultValue = "0", required = false) Integer pageNumber,
            @RequestParam(value = "pageSize", defaultValue = "10", required = false) Integer pageSize,
            @RequestParam(value = "sortBy", defaultValue = "postId", required = false) String sortBy,
            @RequestParam(value = "sortDir", defaultValue = "asc", required = false) String sortDir
    ) {
        return new ResponseEntity<PostResponseDTO>(postsService.getAllPosts(pageNumber, pageSize, sortBy, sortDir), HttpStatus.OK);
    }

    @PutMapping("/update/post/{postId}")
    public ResponseEntity<PostsDTO> updatePost(
            @Valid
            @RequestBody PostsDTO postsDTO,
            @PathVariable Integer postId
    ) {
        return new ResponseEntity<PostsDTO>(postsService.updatePost(postsDTO, postId), HttpStatus.OK);
    }

    @DeleteMapping("/delete/post/{deleteId}")
    public ResponseEntity<BaseResponseDTO> deletePost(@PathVariable Integer postId) {
        postsService.deletePost(postId);
        BaseResponseDTO res = new BaseResponseDTO(true, "Post has been deleted successfully");
        return new ResponseEntity<BaseResponseDTO>(res, HttpStatus.OK);
    }

    @GetMapping("posts/{keyword}")
    public ResponseEntity<List<PostsDTO>> searchPostByTitle(@PathVariable String keyword) {
        List<PostsDTO> res = postsService.searchPostsByTitle(keyword.toLowerCase());
        return new ResponseEntity<List<PostsDTO>>(res, HttpStatus.OK);
    }
}