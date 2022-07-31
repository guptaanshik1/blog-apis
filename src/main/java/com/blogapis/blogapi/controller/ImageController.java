package com.blogapis.blogapi.controller;

import com.blogapis.blogapi.entity.Image;
import com.blogapis.blogapi.payload.FileResponseDTO;
import com.blogapis.blogapi.payload.ImageDTO;
import com.blogapis.blogapi.payload.PostsDTO;
import com.blogapis.blogapi.service.FileService;
import com.blogapis.blogapi.service.PostsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.util.StreamUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.InputStream;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@RestController
@RequestMapping("/api/v1")
public class ImageController {

    @Autowired
    private FileService fileService;

    @Autowired
    private PostsService postsService;

    @Value("${project.image}")
    private String path;

    @PostMapping(value = "post/{postId}/image/upload")
    public ResponseEntity<FileResponseDTO> imageUpload(
            Image img,
            @RequestParam("image") MultipartFile image,
            @PathVariable Integer postId
    ) {
        String fileName = null;
        try {
            fileName = fileService.uploadImage(path, image, img, postId);
        } catch (IOException e) {
            e.printStackTrace();
            return new ResponseEntity<>(new FileResponseDTO(null, "Image cannot be uploaded"), HttpStatus.INTERNAL_SERVER_ERROR);
        }
        return new ResponseEntity<FileResponseDTO>(new FileResponseDTO(fileName, "Image has been uploaded"), HttpStatus.OK);
    }

    @GetMapping("/post/{postId}/images")
    public ResponseEntity<List<String>> getAllImagesOfPost(@PathVariable Integer postId) {
        return new ResponseEntity<List<String>> (
                fileService.getImagesNameOfPost(postId),
                HttpStatus.OK
        );
    }

    @GetMapping(value = "image/{imageName}", produces = MediaType.IMAGE_JPEG_VALUE)
    public void downloadImage(
            @PathVariable String imageName,
            HttpServletResponse response
    ) throws IOException {
        InputStream resource = fileService.getResource(path, imageName);
        response.setContentType(MediaType.IMAGE_JPEG_VALUE);
        StreamUtils.copy(resource, response.getOutputStream());
    }
}