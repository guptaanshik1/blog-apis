package com.blogapis.blogapi.controller;

import com.blogapis.blogapi.service.CloudinaryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.LinkedHashMap;

@RestController
@RequestMapping("/api/v1")
public class CloudinaryController {

    @Autowired
    private CloudinaryService cloudinaryService;

    @PostMapping("/upload/image")
    public ResponseEntity<LinkedHashMap<String, Object>> uploadImage(
            @RequestParam("image") MultipartFile image
    ) throws IOException {
        String url = cloudinaryService.uploadFile(image);
        return null;
    }

}
