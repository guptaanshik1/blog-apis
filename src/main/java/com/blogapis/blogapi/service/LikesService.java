package com.blogapis.blogapi.service;

import com.blogapis.blogapi.payload.LikesDTO;

public interface LikesService {

    public LikesDTO likePosts (LikesDTO likesDTO, Integer userId, Integer postsId);

    public LikesDTO likeComment (LikesDTO likesDTO, Integer userId, Integer commentId);

    public LikesDTO likeReply (LikesDTO likesDTO, Integer userId, Integer replyId);

    public int numberOfLikesOnPost (Integer postId);

    public int numberOfLikesOnComment (Integer commentId);

    public int numberOfLikesOnReply (Integer replyId);

    public LikesDTO dislikePost (LikesDTO likesDTO, Integer postId, Integer userId);

    public LikesDTO dislikeComment (LikesDTO likesDTO, Integer commentId, Integer userId);

    public LikesDTO dislikeReply (LikesDTO likesDTO, Integer replyId, Integer userId);

    public int numberOfDislikesOnPost (Integer postId);

    public int numberOfDislikesOnComment (Integer commentId);

    public int numberOfDislikesOnReply (Integer replyId);

}