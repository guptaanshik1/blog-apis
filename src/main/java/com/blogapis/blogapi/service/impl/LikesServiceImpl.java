package com.blogapis.blogapi.service.impl;

import com.blogapis.blogapi.entity.*;
import com.blogapis.blogapi.exception.ResourceNotFoundException;
import com.blogapis.blogapi.payload.LikesDTO;
import com.blogapis.blogapi.repository.*;
import com.blogapis.blogapi.service.LikesService;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


@Slf4j
@Service
public class LikesServiceImpl implements LikesService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PostsRepository postsRepository;

    @Autowired
    private CommentRepositiry commentRepositiry;

    @Autowired
    private RepliesRepository repliesRepository;

    @Autowired
    private LikesRepository likesRepository;

    @Autowired
    private ModelMapper modelMapper;

    @Override
    public LikesDTO likePosts(LikesDTO likesDTO, Integer userId, Integer postsId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new ResourceNotFoundException("User", "user id", userId));

        Posts post = postsRepository.findById(postsId)
                .orElseThrow(() -> new ResourceNotFoundException("Post", "post id", postsId));

        Likes likes = likesDtoToLikes(likesDTO);

        Boolean isLiked = likesRepository.isPostLikedOrNot(postsId, userId);
        Boolean isDisliked = likesRepository.isPostDislikedOrNot(postsId, userId);

        if (isLiked == null) {
            System.out.println(isDisliked);
            if (isDisliked != null && !isDisliked) likesRepository.deleteDislikeFromPost(postsId, userId);

            likes.setIsLiked(likesDTO.getIsLiked());
            likes.setPosts(post);
            likes.setUser(user);
            Likes savedLike = likesRepository.save(likes);
            LikesDTO toBeSavedLike = likesToLikesDto(savedLike);
            toBeSavedLike.setStatus(true);
            toBeSavedLike.setMessage("Post has been liked");

            return toBeSavedLike;
        } else {
            likesRepository.deleteLikeFromPost(postsId, userId);
            likesDTO.setStatus(true);
            likesDTO.setMessage("Like from the post has been removed");
            return likesDTO;
        }
    }

    @Override
    public LikesDTO likeComment(LikesDTO likesDTO, Integer userId, Integer commentId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new ResourceNotFoundException("User", "user id", userId));

        Comment comment = commentRepositiry.findById(commentId)
                .orElseThrow(() -> new ResourceNotFoundException("Comment", "comment id", commentId));

        Likes likes = likesDtoToLikes(likesDTO);

        Boolean isDisliked = likesRepository.isCommentDislikedOrNot(commentId, userId);
        Boolean isLiked = likesRepository.isCommentLikedOrNot(commentId, userId);
        System.out.println(isLiked);

        if (isLiked == null) {
            if (isDisliked != null && !isDisliked) likesRepository.deleteDislikeFromComment(commentId, userId);
            likes.setIsLiked(likesDTO.getIsLiked());
            likes.setUser(user);
            likes.setComment(comment);
            Likes savedLike = likesRepository.save(likes);
            log.info("Liked Comment");
            LikesDTO toBeSavedDto = likesToLikesDto(savedLike);
            toBeSavedDto.setStatus(true);
            toBeSavedDto.setMessage("Comment has been liked");
            return toBeSavedDto;
        } else {
            likesRepository.deleteLikeFromComment(commentId, userId);
            likesDTO.setStatus(true);
            likesDTO.setMessage("Like from the comment has been removed");
            return likesDTO;
        }
    }

    @Override
    public LikesDTO likeReply(LikesDTO likesDTO, Integer userId, Integer replyId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new ResourceNotFoundException("User", "user id", userId));

        Replies replies = repliesRepository.findById(replyId)
                .orElseThrow(() -> new ResourceNotFoundException("Reply", "reply id", replyId));

        Likes likes = likesDtoToLikes(likesDTO);

        Boolean isDisliked = likesRepository.isReplyDislikedOrNot(replyId, userId);
        Boolean isLiked = likesRepository.isReplyLikedOrNot(replyId, userId);
        if (isLiked == null) {
            if (isDisliked != null && !isDisliked) likesRepository.deleteDislikeFromReply(replyId, userId);

            likes.setIsLiked(likesDTO.getIsLiked());
            likes.setReplies(replies);
            likes.setUser(user);
            Likes savedLike = likesRepository.save(likes);
            LikesDTO toBeSavedLike = likesToLikesDto(savedLike);
            toBeSavedLike.setStatus(true);
            toBeSavedLike.setMessage("Reply has been liked");

            return toBeSavedLike;
        } else {
            likesRepository.deleteLikeFromReply(replyId, userId);
            likesDTO.setStatus(true);
            likesDTO.setMessage("Like from reply has been removed");

            return likesDTO;
        }
    }

    @Override
    public LikesDTO dislikePost(LikesDTO likesDTO, Integer postId, Integer userId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new ResourceNotFoundException("User", "user id", userId));

        Posts posts = postsRepository.findById(postId)
                .orElseThrow(() -> new ResourceNotFoundException("Post", "post id", postId));

        Likes likes = likesDtoToLikes(likesDTO);

        Boolean isLiked = likesRepository.isPostLikedOrNot(postId, userId);
        Boolean isDisliked = likesRepository.isPostDislikedOrNot(postId, userId);
        if (isDisliked == null) {
            if (isLiked != null && isLiked) likesRepository.deleteLikeFromPost(postId, userId);

            likes.setIsLiked(likesDTO.getIsLiked());
            likes.setUser(user);
            likes.setPosts(posts);
            Likes savedLike = likesRepository.save(likes);
            LikesDTO toBeSavedDislike = likesToLikesDto(savedLike);
            toBeSavedDislike.setStatus(true);
            toBeSavedDislike.setMessage("Post has been disliked");

            return toBeSavedDislike;
        } else {
            likesRepository.deleteDislikeFromPost(postId, userId);
            likesDTO.setStatus(true);
            likesDTO.setMessage("Dislike from the post has been removed");
            return likesDTO;
        }
    }

    @Override
    public LikesDTO dislikeComment(LikesDTO likesDTO, Integer commentId, Integer userId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new ResourceNotFoundException("User", "user id", userId));

        Comment comment = commentRepositiry.findById(commentId)
                .orElseThrow(() -> new ResourceNotFoundException("Comment", "comment id", commentId));

        Likes likes = likesDtoToLikes(likesDTO);

        Boolean isLiked = likesRepository.isCommentLikedOrNot(commentId, userId);
        Boolean isDisliked = likesRepository.isCommentDislikedOrNot(commentId, userId);

        if (isDisliked == null) {
            if (isLiked != null && isLiked) likesRepository.deleteDislikeFromComment(commentId, userId);

            likes.setIsLiked(likesDTO.getIsLiked());
            likes.setComment(comment);
            likes.setUser(user);
            Likes savedLike = likesRepository.save(likes);
            LikesDTO toBeSavedLike = likesToLikesDto(savedLike);
            toBeSavedLike.setStatus(true);
            toBeSavedLike.setMessage("Comment has been disliked");
            return toBeSavedLike;
        } else {
            likesRepository.deleteDislikeFromComment(commentId, userId);
            likesDTO.setStatus(true);
            likesDTO.setMessage("Dislike from the comment has removed");
            return likesDTO;
        }
    }

    @Override
    public LikesDTO dislikeReply(LikesDTO likesDTO, Integer replyId, Integer userId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new ResourceNotFoundException("User", "user id", userId));

        Replies replies = repliesRepository.findById(replyId)
                .orElseThrow(() -> new ResourceNotFoundException("Reply", "reply id", replyId));

        Likes likes = likesDtoToLikes(likesDTO);

        Boolean isDisliked = likesRepository.isReplyDislikedOrNot(replyId, userId);
        Boolean isLiked = likesRepository.isReplyLikedOrNot(replyId, userId);

        if (isDisliked == null) {
            if (isLiked != null && isLiked) likesRepository.deleteLikeFromReply(replyId, userId);

            likes.setIsLiked(likesDTO.getIsLiked());
            likes.setReplies(replies);
            likes.setUser(user);
            Likes savedLike = likesRepository.save(likes);
            LikesDTO toBeSavedLike = likesToLikesDto(savedLike);
            toBeSavedLike.setStatus(true);
            toBeSavedLike.setMessage("Reply has been disliked");
            return toBeSavedLike;
        } else {
            likesRepository.deleteDislikeFromReply(replyId, userId);
            likesDTO.setStatus(true);
            likesDTO.setMessage("Dislike from the reply has removed");
            return likesDTO;
        }
    }

    @Override
    public int numberOfLikesOnPost(Integer postId) {
        return likesRepository.countPostLikes(postId);
    }

    @Override
    public int numberOfLikesOnComment(Integer commentId) {
        return likesRepository.countCommentLikes(commentId);
    }

    @Override
    public int numberOfLikesOnReply(Integer replyId) {
        return likesRepository.countReplyLikes(replyId);
    }

    @Override
    public int numberOfDislikesOnPost(Integer postId) {
        return likesRepository.countPostDislikes(postId);
    }

    @Override
    public int numberOfDislikesOnComment(Integer commentId) {
        return likesRepository.countCommentDislikes(commentId);
    }

    @Override
    public int numberOfDislikesOnReply(Integer replyId) {
        return likesRepository.countReplyDislikes(replyId);
    }

    private Likes likesDtoToLikes (LikesDTO likesDTO) {
        return modelMapper.map(likesDTO, Likes.class);
    }

    private LikesDTO likesToLikesDto (Likes likes) {
        return modelMapper.map(likes, LikesDTO.class);
    }

}