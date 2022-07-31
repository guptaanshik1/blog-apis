package com.blogapis.blogapi.service.impl;

import com.blogapis.blogapi.entity.Comment;
import com.blogapis.blogapi.entity.Posts;
import com.blogapis.blogapi.entity.User;
import com.blogapis.blogapi.exception.ResourceNotFoundException;
import com.blogapis.blogapi.payload.CommentDTO;
import com.blogapis.blogapi.repository.CommentRepositiry;
import com.blogapis.blogapi.repository.PostsRepository;
import com.blogapis.blogapi.repository.UserRepository;
import com.blogapis.blogapi.service.CommentService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class CommentServiceImpl implements CommentService {

    @Autowired
    private CommentRepositiry commentRepositiry;

    @Autowired
    private PostsRepository postsRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private ModelMapper modelMapper;

    @Override
    public CommentDTO createComment(CommentDTO commentDTO, Integer userId, Integer postId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new ResourceNotFoundException("User", "userId", userId));
        Posts posts = postsRepository.findById(postId)
                .orElseThrow(() -> new ResourceNotFoundException("Posts", "postId", postId));

        Comment comment = dtoToComment(commentDTO);
        comment.setContent(commentDTO.getContent());
        comment.setPosts(posts);
        comment.setUser(user);

        Comment createdComment = commentRepositiry.save(comment);
        return commentToDto(createdComment);
    }

    @Override
    public CommentDTO updateComment(CommentDTO commentDTO, Integer commentId) {
        Comment comment = commentRepositiry.findById(commentId)
                .orElseThrow(() -> new ResourceNotFoundException("Comment", "coomentId", commentId));

        comment.setContent(commentDTO.getContent());

        Comment updatedComment = commentRepositiry.save(comment);
        return commentToDto(updatedComment);
    }

    @Override
    public void deleteComment(Integer commentId) {
        Comment comment = commentRepositiry.findById(commentId)
                .orElseThrow(() -> new ResourceNotFoundException("Comment", "commentId", commentId));

        commentRepositiry.delete(comment);
    }

    @Override
    public List<CommentDTO> getAllCommentsForPost(Integer postId) {
        Posts posts = postsRepository.findById(postId)
                .orElseThrow(() -> new ResourceNotFoundException("Posts", "postId", postId));

        List<Comment> comments = commentRepositiry.findByPosts(posts);
        List <CommentDTO> commentDTOS = comments.stream().map(comment -> commentToDto(comment))
                .collect(Collectors.toList());

        return commentDTOS;
    }

    @Override
    public List<CommentDTO> getAllCommentsForUser(Integer userId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new ResourceNotFoundException("User", "userId", userId));

        List<Comment> comments = commentRepositiry.findByUser(user);
        List <CommentDTO> commentDTOS = comments.stream().map(comment -> commentToDto(comment))
                .collect(Collectors.toList());

        return commentDTOS;
    }


    private CommentDTO commentToDto(Comment comment) {
        return modelMapper.map(comment, CommentDTO.class);
    }

    private Comment dtoToComment(CommentDTO commentDTO) {
        return modelMapper.map(commentDTO, Comment.class);
    }
}
