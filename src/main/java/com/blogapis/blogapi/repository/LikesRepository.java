package com.blogapis.blogapi.repository;

import com.blogapis.blogapi.entity.Likes;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;

@Repository
public interface LikesRepository extends JpaRepository<Likes, Integer> {

    @Query(value = " SELECT COUNT(is_liked) " +
            " FROM likes " +
            " WHERE posts_post_id = :postId " +
            " AND is_liked = 1 ",
        nativeQuery = true
    )
    public int countPostLikes (int postId);

    @Query(value = " SELECT COUNT(is_liked) " +
            " FROM likes " +
            " WHERE comment_id = :commentId " +
            " AND is_liked = 1 ",
            nativeQuery = true
    )
    public int countCommentLikes (int commentId);

    @Query(value = " SELECT COUNT(*) " +
            " FROM likes " +
            " WHERE replies_id = :replyId " +
            " AND is_liked = 1 ",
            nativeQuery = true
    )
    public int countReplyLikes (int replyId);

    @Query(value = " SELECT COUNT(is_liked) " +
            " FROM likes " +
            " WHERE posts_post_id = :postId " +
            " AND is_liked = 0 ",
            nativeQuery = true
    )
    public int countPostDislikes (int postId);

    @Query(value = " SELECT COUNT(is_liked) " +
            " FROM likes " +
            " WHERE comment_id = :commentId " +
            " AND is_liked = 0 ",
            nativeQuery = true
    )
    public int countCommentDislikes (int commentId);

    @Query(value = " SELECT COUNT(*) " +
            " FROM likes " +
            " WHERE replies_id = :replyId " +
            " AND is_liked = 0 ",
            nativeQuery = true
    )
    public int countReplyDislikes (int replyId);

    @Query(value = " SELECT is_liked " +
            " FROM likes " +
            " WHERE posts_post_id = :postId " +
            " AND user_id = :userId " +
            " AND is_liked = 1 ",
            nativeQuery = true
    )
    public Boolean isPostLikedOrNot (int postId, int userId);

    @Query(value = " SELECT is_liked " +
            " FROM likes " +
            " WHERE comment_id = :commentId " +
            " AND user_id = :userId " +
            " AND is_liked = 1 ",
            nativeQuery = true
    )
    public Boolean isCommentLikedOrNot (int commentId, int userId);

    @Query(value = " SELECT is_liked " +
            " FROM likes " +
            " WHERE replies_id = :replyId " +
            " AND user_id = :userId " +
            " AND is_liked = 1 ",
            nativeQuery = true
    )
    public Boolean isReplyLikedOrNot (int replyId, int userId);

    @Query(value = " SELECT is_liked " +
            " FROM likes " +
            " WHERE posts_post_id = :postId " +
            " AND user_id = :userId " +
            " AND is_liked = 0 ",
            nativeQuery = true
    )
    public Boolean isPostDislikedOrNot (int postId, int userId);

    @Query(value = " SELECT is_liked " +
            " FROM likes " +
            " WHERE comment_id = :commentId " +
            " AND user_id = :userId " +
            " AND is_liked = 0 ",
            nativeQuery = true
    )
    public Boolean isCommentDislikedOrNot (int commentId, int userId);

    @Query(value = " SELECT is_liked " +
            " FROM likes " +
            " WHERE replies_id = :replyId " +
            " AND user_id = :userId " +
            " AND is_liked = 0 ",
            nativeQuery = true
    )
    public Boolean isReplyDislikedOrNot (int replyId, int userId);

    @Modifying
    @Transactional
    @Query(value = " DELETE FROM likes " +
            " WHERE posts_post_id = :postId " +
            " AND user_id = :userId " +
            " AND is_liked = 1 ",
            nativeQuery = true
    )
    public void deleteLikeFromPost(int postId, int userId);

    @Modifying
    @Transactional
    @Query(value = " DELETE FROM likes " +
            " WHERE comment_id = :commentId " +
            " AND user_id = :userId " +
            " AND is_liked = 1 ",
            nativeQuery = true
    )
    public void deleteLikeFromComment(int commentId, int userId);

    @Modifying
    @Transactional
    @Query(value = " DELETE FROM likes " +
            " WHERE replies_id = :replyId " +
            " AND user_id = :userId " +
            " AND is_liked = 1 ",
            nativeQuery = true
    )
    public void deleteLikeFromReply(int replyId, int userId);

    @Modifying
    @Transactional
    @Query(value = " DELETE FROM likes " +
            " WHERE posts_post_id = :postId " +
            " AND user_id = :userId " +
            " AND is_liked = 0 ",
            nativeQuery = true
    )
    public void deleteDislikeFromPost(int postId, int userId);

    @Modifying
    @Transactional
    @Query(value = " DELETE FROM likes " +
            " WHERE comment_id = :commentId " +
            " AND user_id = :userId " +
            " AND is_liked = 0 ",
            nativeQuery = true
    )
    public void deleteDislikeFromComment(int commentId, int userId);

    @Modifying
    @Transactional
    @Query(value = " DELETE FROM likes " +
            " WHERE replies_id = :replyId " +
            " AND user_id = :userId " +
            " AND is_liked = 0 ",
            nativeQuery = true
    )
    public void deleteDislikeFromReply(int replyId, int userId);
}