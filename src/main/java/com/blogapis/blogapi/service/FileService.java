package com.blogapis.blogapi.service;

import com.blogapis.blogapi.entity.Image;
import org.springframework.web.multipart.MultipartFile;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;

public interface FileService {

    String uploadImage (String path, MultipartFile file, Image image, Integer postId) throws IOException;

    InputStream getResource (String path, String fileName) throws FileNotFoundException;

    List<String> getImagesNameOfPost(Integer postId);

}
