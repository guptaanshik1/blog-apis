package com.blogapis.blogapi.service.impl;

import com.blogapis.blogapi.service.CloudinaryService;
import com.cloudinary.Cloudinary;
import com.cloudinary.utils.ObjectUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Map;

@Slf4j
@Service
public class CloudinaryServiceImpl implements CloudinaryService {

    @Autowired
    private Cloudinary cloudinaryConfig;

    @Override
    public String uploadFile(MultipartFile file) {
        try {
            File uploadedFile = convertMultipartToFile(file);
            Map uploadedResult = cloudinaryConfig.uploader()
                    .upload(uploadedFile, ObjectUtils.emptyMap());

            boolean isDeleted = uploadedFile.delete();

            if (isDeleted) log.info("File is deleted successfully");
            else log.info("File does not exist");

            return uploadedResult.get("url").toString();
        } catch (Exception ex) {
            throw new RuntimeException(ex);
        }
    }

    private File convertMultipartToFile(MultipartFile file) throws IOException {
        File convertedFile = new File(file.getOriginalFilename());
        FileOutputStream fos = new FileOutputStream(convertedFile);
        fos.write(file.getBytes());
        fos.close();
        return convertedFile;
    }


}
