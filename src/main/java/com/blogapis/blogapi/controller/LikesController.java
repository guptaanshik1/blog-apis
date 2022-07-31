package com.blogapis.blogapi.controller;

import com.blogapis.blogapi.payload.BaseResponseDTO;
import com.blogapis.blogapi.payload.LikesDTO;
import com.blogapis.blogapi.service.LikesService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping("/api/v1")
public class LikesController {

    @Autowired
    private LikesService likesService;

    @PostMapping("/user/{userId}/post/{postId}/like")
    public ResponseEntity<BaseResponseDTO> likePost (
            @Valid
            @RequestBody LikesDTO likesDTO,
            @PathVariable Integer userId,
            @PathVariable Integer postId
            ) {
        return new ResponseEntity<BaseResponseDTO>(
          likesService.likePosts(likesDTO, userId, postId),
          HttpStatus.CREATED
        );
    }

    @PostMapping("/user/{userId}/comment/{commentId}/like")
    public ResponseEntity<BaseResponseDTO> likeComment (
            @Valid
            @RequestBody LikesDTO likesDTO,
            @PathVariable Integer userId,
            @PathVariable Integer commentId
    ) {
        return new ResponseEntity<BaseResponseDTO>(
                likesService.likeComment(likesDTO, userId, commentId),
                HttpStatus.CREATED
        );
    }

    @PostMapping("/user/{userId}/reply/{replyId}/like")
    public ResponseEntity<BaseResponseDTO> likeReply (
            @Valid
            @RequestBody LikesDTO likesDTO,
            @PathVariable Integer userId,
            @PathVariable Integer replyId
    ) {
        return new ResponseEntity<BaseResponseDTO>(
                likesService.likeReply(likesDTO, userId, replyId),
                HttpStatus.CREATED
        );
    }

    @PostMapping("/user/{userId}/post/{postId}/dislike")
    public ResponseEntity<BaseResponseDTO> dislikePost (
            @RequestBody LikesDTO likesDTO,
            @PathVariable Integer postId,
            @PathVariable Integer userId
            ) {
        return new ResponseEntity<BaseResponseDTO>(
                likesService.dislikePost(likesDTO, postId, userId),
                HttpStatus.OK
        );
    }

    @PostMapping("/user/{userId}/comment/{commentId}/dislike")
    public ResponseEntity<BaseResponseDTO> dislikeComment (
            @RequestBody LikesDTO likesDTO,
            @PathVariable Integer commentId,
            @PathVariable Integer userId
    ) {
        return new ResponseEntity<BaseResponseDTO>(
                likesService.dislikeComment(likesDTO, commentId, userId),
                HttpStatus.OK
        );
    }

    @PostMapping("/user/{userId}/reply/{replyId}/dislike")
    public ResponseEntity<BaseResponseDTO> dislikeReply (
            @RequestBody LikesDTO likesDTO,
            @PathVariable Integer replyId,
            @PathVariable Integer userId
    ) {
        return new ResponseEntity<BaseResponseDTO>(
                likesService.dislikeReply(likesDTO, replyId, userId),
                HttpStatus.OK
        );
    }

    @GetMapping("/post/{postId}/get-likes")
    public ResponseEntity<Integer> getNumberOfLikesOnPost (@PathVariable Integer postId) {
        return new ResponseEntity<Integer>(
                likesService.numberOfLikesOnPost(postId),
                HttpStatus.OK
        );
    }

    @GetMapping("comment/{commentId}/get-likes")
    public ResponseEntity<Integer> getNumberOfLikesOnComment (@PathVariable Integer commentId) {
        return new ResponseEntity<Integer>(
                likesService.numberOfLikesOnComment(commentId),
                HttpStatus.OK
        );
    }

    @GetMapping("reply/{replyId}/get-likes")
    public ResponseEntity<Integer> getNumberOfLikesOnReply (@PathVariable Integer replyId) {
        return new ResponseEntity<Integer>(
                likesService.numberOfLikesOnReply(replyId),
                HttpStatus.OK
        );
    }

    @GetMapping("post/{postId}/get-dislikes")
    public ResponseEntity<Integer> getNumberOfDislikesOnPost (@PathVariable Integer postId) {
        return new ResponseEntity<Integer>(
                likesService.numberOfDislikesOnPost(postId),
                HttpStatus.OK
        );
    }

    @GetMapping("comment/{commentId}/get-dislikes")
    public ResponseEntity<Integer> getNumberOfDislikesOnComment (@PathVariable Integer commentId) {
        return new ResponseEntity<Integer>(
                likesService.numberOfDislikesOnComment(commentId),
                HttpStatus.OK
        );
    }

    @GetMapping("reply/{replyId}/get-dislikes")
    public ResponseEntity<Integer> getNumberOfDislikesOnReply (@PathVariable Integer replyId) {
        return new ResponseEntity<Integer>(
                likesService.numberOfDislikesOnReply(replyId),
                HttpStatus.OK
        );
    }
}
