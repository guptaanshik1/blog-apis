package com.blogapis.blogapi.repository;

import com.blogapis.blogapi.entity.Category;
import com.blogapis.blogapi.entity.Posts;
import com.blogapis.blogapi.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PostsRepository extends JpaRepository<Posts, Integer> {
    List<Posts> findByCategory(Category category);

    List<Posts> findByUser(User user);

    @Query(value = "SELECT * FROM posts p WHERE p.post_title LIKE :title", nativeQuery = true)
    List<Posts> searchByTitle(String title);
}