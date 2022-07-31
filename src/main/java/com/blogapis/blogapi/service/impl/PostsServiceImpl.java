package com.blogapis.blogapi.service.impl;

import com.blogapis.blogapi.entity.Category;
import com.blogapis.blogapi.entity.Posts;
import com.blogapis.blogapi.entity.User;
import com.blogapis.blogapi.exception.ResourceNotFoundException;
import com.blogapis.blogapi.payload.PostResponseDTO;
import com.blogapis.blogapi.payload.PostsDTO;
import com.blogapis.blogapi.repository.CategoryRepository;
import com.blogapis.blogapi.repository.PostsRepository;
import com.blogapis.blogapi.repository.UserRepository;
import com.blogapis.blogapi.service.PostsService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.data.web.SpringDataWebProperties;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class PostsServiceImpl implements PostsService {

    @Autowired
    private PostsRepository postsRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private CategoryRepository categoryRepository;

    @Autowired
    private ModelMapper modelMapper;

    @Override
    public PostsDTO createPost(PostsDTO postsDTO, Integer userId, Integer categoryId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new ResourceNotFoundException("User", "userId", userId));

        Category category = categoryRepository.findById(categoryId)
                .orElseThrow(() -> new ResourceNotFoundException("Category", "categoryId", categoryId));

        Posts posts = dtoToPosts(postsDTO);
        posts.setPostContent(postsDTO.getPostContent());
        posts.setAddedDate(new Date());
        posts.setUser(user);
        posts.setCategory(category);

        Posts createdPost = postsRepository.save(posts);

        return postsToDto(createdPost);
    }

    @Override
    public PostsDTO getPost(Integer postId) {
        Posts posts = postsRepository.findById(postId)
                .orElseThrow(() -> new ResourceNotFoundException("Posts", "postId", postId));

        return postsToDto(posts);
    }

    @Override
    public PostResponseDTO getAllPosts(Integer pageNumber, Integer pageSize, String sortBy, String sortDir) {

        Sort sort = sortDir.equalsIgnoreCase("asc") ?
                Sort.by(sortBy).ascending() : Sort.by(sortBy).descending();

        Pageable pageable = PageRequest.of(pageNumber, pageSize, sort);
        Page<Posts> pagePosts = postsRepository.findAll(pageable);

        List<Posts> allPosts = pagePosts.getContent();
        List<PostsDTO> postsDTO = allPosts.stream().map(post -> postsToDto(post)).collect(Collectors.toList());

        PostResponseDTO postResponseDTO = new PostResponseDTO();
        postResponseDTO.setContent(postsDTO);
        postResponseDTO.setPageNumber(pagePosts.getNumber());
        postResponseDTO.setPageSize(pagePosts.getSize());
        postResponseDTO.setTotalElements(pagePosts.getTotalElements());
        postResponseDTO.setTotalPages(pagePosts.getTotalPages());
        postResponseDTO.setFirstPage(pagePosts.isFirst());
        postResponseDTO.setLastPage(pagePosts.isLast());

        return postResponseDTO;
    }

    @Override
    public PostsDTO updatePost(PostsDTO postsDTO, Integer postId) {
        Posts posts = postsRepository.findById(postId)
                .orElseThrow(() -> new ResourceNotFoundException("Posts", "postId", postId));

        posts.setPostTitle(postsDTO.getPostTitle());
        posts.setPostContent(postsDTO.getPostContent());

        Posts updatedPost = postsRepository.save(posts);
        return postsToDto(updatedPost);
    }

    @Override
    public void deletePost(Integer postId) {
        postsRepository.deleteById(postId);
    }

    @Override
    public List<PostsDTO> getPostsByCategory(Integer categoryId) {
        Category category = categoryRepository.findById(categoryId)
                .orElseThrow(() -> new ResourceNotFoundException("Category", "categoryId", categoryId));

        List<Posts> posts = postsRepository.findByCategory(category);

        List<PostsDTO> postsDTOS = posts.stream().map((post) -> postsToDto(post)).collect(Collectors.toList());
        return postsDTOS;
    }

    @Override
    public List<PostsDTO> getPostsByUser(Integer userId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new ResourceNotFoundException("User", "userId", userId));

        List<Posts> posts = postsRepository.findByUser(user);

        List<PostsDTO> postsDTOS = posts.stream().map((post) -> postsToDto(post)).collect(Collectors.toList());
        return postsDTOS;
    }

    @Override
    public List<PostsDTO> searchPostsByTitle(String keyword) {
        List<Posts> posts = postsRepository.searchByTitle("%" + keyword + "%");
        List<PostsDTO> postsDTOS = posts.stream().map(post -> postsToDto(post)).collect(Collectors.toList());

        return postsDTOS;
    }

    @Override
    public PostsDTO getPostById(Integer postId) {
        Posts posts = postsRepository.findById(postId)
                .orElseThrow(() -> new ResourceNotFoundException("Posts", "postId", postId));
        return postsToDto(posts);
    }

    private PostsDTO postsToDto(Posts posts) {
        PostsDTO postsDTO = modelMapper.map(posts, PostsDTO.class);
        return postsDTO;
    }

    private Posts dtoToPosts(PostsDTO postsDTO) {
        Posts posts = modelMapper.map(postsDTO, Posts.class);
        return posts;
    }
}