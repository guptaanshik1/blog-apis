package com.blogapis.blogapi.controller;

import com.blogapis.blogapi.payload.BaseResponseDTO;
import com.blogapis.blogapi.payload.CommentDTO;
import com.blogapis.blogapi.service.CommentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/api/v1")
public class CommentController {

    @Autowired
    private CommentService commentService;

    @PostMapping("/user/{userId}/post/{postId}/comments")
    public ResponseEntity<CommentDTO> createComment(
            @Valid
            @RequestBody CommentDTO commentDTO,
            @PathVariable Integer userId,
            @PathVariable Integer postId
            ) {
        return new ResponseEntity<CommentDTO>(
                commentService.createComment(commentDTO, userId, postId),
                HttpStatus.CREATED
        );
    }

    @DeleteMapping("/comment/{commentId}")
    public ResponseEntity<BaseResponseDTO> deleteComment(@PathVariable Integer commentId) {
        commentService.deleteComment(commentId);
        BaseResponseDTO res = new BaseResponseDTO(true, "Comment has been deleted successfully");
        return new ResponseEntity<BaseResponseDTO>(res, HttpStatus.OK);
    }

    @PutMapping("/update/comment/{commentId}")
    public ResponseEntity<CommentDTO> updateComment(
            @Valid
            @RequestBody CommentDTO commentDTO,
            @PathVariable Integer commentId
    ) {
        return new ResponseEntity<CommentDTO>(
                commentService.updateComment(commentDTO, commentId),
                HttpStatus.OK
        );
    }

    @GetMapping("/post/{postId}/comments")
    public ResponseEntity<List<CommentDTO>> getAllCommentsForPosts(@PathVariable Integer postId) {
        return new ResponseEntity<List<CommentDTO>>(
          commentService.getAllCommentsForPost(postId),
          HttpStatus.OK
        );
    }

    @GetMapping("/user/{userId}/comments")
    public ResponseEntity<List<CommentDTO>> getAllCommentsByUser(@PathVariable Integer userId) {
        return new ResponseEntity<List<CommentDTO>>(
          commentService.getAllCommentsForUser(userId),
          HttpStatus.OK
        );
    }
}