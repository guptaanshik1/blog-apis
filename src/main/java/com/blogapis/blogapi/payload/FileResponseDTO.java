package com.blogapis.blogapi.payload;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class FileResponseDTO {
    private String fileName;
    private String message;
    private String url;
    private PostsDTO postsDTO;

    public FileResponseDTO(String fileName, String message) {
        this.fileName = fileName;
        this.message = message;
    }
}
