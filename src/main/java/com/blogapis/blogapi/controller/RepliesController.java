package com.blogapis.blogapi.controller;

import com.blogapis.blogapi.payload.BaseResponseDTO;
import com.blogapis.blogapi.payload.RepliesDTO;
import com.blogapis.blogapi.service.RepliesService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/api/v1")
public class RepliesController {

    @Autowired
    private RepliesService repliesService;

    @PostMapping("/user/{userId}/comment/{commentId}/add_reply")
    public ResponseEntity<RepliesDTO> addReply(
            @Valid
            @RequestBody RepliesDTO repliesDTO,
            @PathVariable Integer userId,
            @PathVariable Integer commentId
    ) {
        RepliesDTO res = repliesService.createReply(repliesDTO, userId, commentId);
        return new ResponseEntity<RepliesDTO>(res, HttpStatus.CREATED);
    }

    @PutMapping("/reply/update/{replyId}")
    public ResponseEntity<RepliesDTO> updateReply(
            @RequestBody RepliesDTO repliesDTO,
            @PathVariable Integer replyId
    ) {
        RepliesDTO res = repliesService.updateReply(repliesDTO, replyId);
        return new ResponseEntity<RepliesDTO>(res, HttpStatus.OK);
    }

    @GetMapping("/reply/{replyId}")
    public ResponseEntity<RepliesDTO> getReplyById(@PathVariable Integer replyId) {
        return new ResponseEntity<RepliesDTO>(
          repliesService.getReply(replyId),
          HttpStatus.OK
        );
    }

    @DeleteMapping("reply/delete/{replyId}")
    public BaseResponseDTO deleteReplyById(@PathVariable Integer replyId) {
        repliesService.deleteReply(replyId);
        return new BaseResponseDTO(true, "Reply has been deleted");
    }

    @GetMapping("/comment/{commentId}/replies")
    public ResponseEntity<List<RepliesDTO>> getRepliesOfComment(@PathVariable Integer commentId) {
        List<RepliesDTO> replies = repliesService.getRepliesOfComment(commentId);
        return new ResponseEntity<List<RepliesDTO>>(replies, HttpStatus.OK);
    }
}