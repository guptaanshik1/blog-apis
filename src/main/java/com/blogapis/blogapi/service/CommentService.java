package com.blogapis.blogapi.service;

import com.blogapis.blogapi.payload.CommentDTO;
import com.blogapis.blogapi.payload.ServiceResult;

import java.util.List;

public interface CommentService {

    public ServiceResult createComment(CommentDTO commentDTO, Integer userId, Integer postId);

    public CommentDTO updateComment(CommentDTO commentDTO, Integer commentId);

    public void deleteComment(Integer commentId);

    public List<CommentDTO> getAllCommentsForPost(Integer postId);

    public List<CommentDTO> getAllCommentsForUser(Integer userId);

}
