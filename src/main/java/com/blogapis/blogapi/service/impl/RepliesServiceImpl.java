package com.blogapis.blogapi.service.impl;

import com.blogapis.blogapi.entity.Comment;
import com.blogapis.blogapi.entity.Replies;
import com.blogapis.blogapi.entity.User;
import com.blogapis.blogapi.exception.ResourceNotFoundException;
import com.blogapis.blogapi.payload.RepliesDTO;
import com.blogapis.blogapi.repository.CommentRepositiry;
import com.blogapis.blogapi.repository.RepliesRepository;
import com.blogapis.blogapi.repository.UserRepository;
import com.blogapis.blogapi.service.RepliesService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class RepliesServiceImpl implements RepliesService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private CommentRepositiry commentRepositiry;

    @Autowired
    private RepliesRepository repliesRepository;

    @Autowired
    private ModelMapper modelMapper;

    @Override
    public RepliesDTO createReply(RepliesDTO repliesDTO, Integer userId, Integer commentId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new ResourceNotFoundException("User", "userId", userId));
        Comment comment = commentRepositiry.findById(commentId)
                .orElseThrow(() -> new ResourceNotFoundException("Comment", "commentId", commentId));

        Replies replies = repliesDtoToReplies(repliesDTO);
        replies.setContent(repliesDTO.getContent());
        replies.setUser(user);
        replies.setComment(comment);

        Replies createdReply = repliesRepository.save(replies);
        return repliesToRepliesDTO(createdReply);
    }

    @Override
    public RepliesDTO updateReply(RepliesDTO repliesDTO, Integer replyId) {
        Replies replies = repliesRepository.findById(replyId)
                .orElseThrow(() -> new ResourceNotFoundException("Replies", "replyId", replyId));

        replies.setContent(repliesDTO.getContent());
        Replies updatedReply = repliesRepository.save(replies);

        return repliesToRepliesDTO(updatedReply);
    }

    @Override
    public RepliesDTO getReply(Integer replyId) {
        Replies replies = repliesRepository.findById(replyId)
                .orElseThrow(() -> new ResourceNotFoundException("Replies", "replyId", replyId));

        return repliesToRepliesDTO(replies);
    }

    @Override
    public List<RepliesDTO> getRepliesOfComment(Integer commentId) {
        Comment comment = commentRepositiry.findById(commentId)
                .orElseThrow(() -> new ResourceNotFoundException("Comment", "commentId", commentId));

        List<Replies> replies = repliesRepository.findByComment(comment);
        List<RepliesDTO> repliesDTOS = replies.stream()
                .map(reply -> repliesToRepliesDTO(reply)).collect(Collectors.toList());

        return repliesDTOS;
    }

    @Override
    public void deleteReply(Integer replyId) {
        Replies reply = repliesRepository.findById(replyId)
                .orElseThrow(() -> new ResourceNotFoundException("Replies", "replyId", replyId));

        repliesRepository.delete(reply);
    }

    private RepliesDTO repliesToRepliesDTO(Replies replies) {
        return modelMapper.map(replies, RepliesDTO.class);
    }

    private Replies repliesDtoToReplies(RepliesDTO repliesDTO) {
        return modelMapper.map(repliesDTO, Replies.class);
    }
}