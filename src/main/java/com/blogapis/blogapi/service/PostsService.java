package com.blogapis.blogapi.service;

import com.blogapis.blogapi.payload.PostResponseDTO;
import com.blogapis.blogapi.payload.PostsDTO;

import java.util.List;

public interface PostsService {

    public PostsDTO createPost(PostsDTO postsDTO, Integer userId, Integer categoryId);

    public PostsDTO getPost(Integer postId);

    public PostResponseDTO getAllPosts(Integer pageNumber, Integer pageSize, String sortBy, String sortDir);

    public PostsDTO updatePost(PostsDTO postsDTO, Integer postId);

    public void deletePost(Integer postId);

    public PostsDTO getPostById(Integer postId);

    public List<PostsDTO> getPostsByCategory(Integer categoryId);

    public List<PostsDTO> getPostsByUser(Integer userId);

    public List<PostsDTO> searchPostsByTitle(String keyword);

}