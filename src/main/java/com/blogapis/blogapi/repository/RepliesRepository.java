package com.blogapis.blogapi.repository;

import com.blogapis.blogapi.entity.Comment;
import com.blogapis.blogapi.entity.Replies;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface RepliesRepository extends JpaRepository<Replies, Integer> {

    List<Replies> findByComment (Comment comment);

}