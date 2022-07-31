package com.blogapis.blogapi.service.impl;

import com.blogapis.blogapi.entity.Image;
import com.blogapis.blogapi.entity.Posts;
import com.blogapis.blogapi.exception.ResourceNotFoundException;
import com.blogapis.blogapi.payload.ImageDTO;
import com.blogapis.blogapi.payload.PostsDTO;
import com.blogapis.blogapi.repository.ImageRepository;
import com.blogapis.blogapi.repository.PostsRepository;
import com.blogapis.blogapi.service.FileService;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;
import java.util.UUID;

@Slf4j
@Service
public class FileServiceImpl implements FileService {

    @Autowired
    private PostsRepository postsRepository;

    @Autowired
    private ImageRepository imageRepository;

    @Autowired
    private ModelMapper modelMapper;

    @Override
    public String uploadImage(String path, MultipartFile file, Image image, Integer postId) throws IOException {
        String name = file.getOriginalFilename();
        String randomId = UUID.randomUUID().toString();
        String fileName = randomId.concat(name.substring(name.lastIndexOf(".")));
        log.info("Random generated file name :: {}", fileName);

        String filePath = path + File.separator + fileName;

        File f = new File(path);
        if (!f.exists()) f.mkdir();

        Files.copy(file.getInputStream(), Paths.get(filePath));

        Posts posts = postsRepository.findById(postId)
                .orElseThrow(() -> new ResourceNotFoundException("Posts", "postId", postId));

        image.setPosts(posts);
        image.setFileName(fileName);

        imageRepository.save(image);

        return fileName;
    }

    @Override
    public InputStream getResource(String path, String fileName) throws FileNotFoundException {
        String fullPath = path + File.separator + fileName;
        InputStream is = new FileInputStream(fullPath);

        return is;
    }

    private ImageDTO imageToImageDto(Image image) {
        return modelMapper.map(image, ImageDTO.class);
    }

    private Image imageDtoToImage(ImageDTO imageDTO) {
        return modelMapper.map(imageDTO, Image.class);
    }

    @Override
    public List<String> getImagesNameOfPost(Integer postId) {
        return imageRepository.fileNames(postId);
    }
}