package com.blogapis.blogapi.service;

import com.blogapis.blogapi.entity.Replies;
import com.blogapis.blogapi.payload.RepliesDTO;

import java.util.List;

public interface RepliesService {

    public RepliesDTO createReply (RepliesDTO repliesDTO, Integer userId, Integer commentId);

    public RepliesDTO updateReply (RepliesDTO repliesDTO, Integer replyId);

    public RepliesDTO getReply (Integer replyId);

    public List<RepliesDTO> getRepliesOfComment (Integer commentId);

    public void deleteReply (Integer replyId);

}