package com.blogapis.blogapi.service;

import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;

public interface CloudinaryService {

    public String uploadFile(MultipartFile file);

}
