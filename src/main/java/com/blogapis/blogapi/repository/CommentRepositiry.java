package com.blogapis.blogapi.repository;

import com.blogapis.blogapi.entity.Comment;
import com.blogapis.blogapi.entity.Posts;
import com.blogapis.blogapi.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CommentRepositiry extends JpaRepository<Comment, Integer> {
    List<Comment> findByPosts(Posts posts);

    List<Comment> findByUser(User user);
}