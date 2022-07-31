package com.blogapis.blogapi.repository;

import com.blogapis.blogapi.entity.Image;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ImageRepository extends JpaRepository<Image, Integer> {

    @Query(value = " SELECT file_name FROM image " +
            " WHERE posts_post_id = :postId ",
            nativeQuery = true
    )
    List<String> fileNames (Integer postId);

}