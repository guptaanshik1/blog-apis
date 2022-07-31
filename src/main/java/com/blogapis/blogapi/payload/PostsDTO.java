package com.blogapis.blogapi.payload;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.NotBlank;
import java.util.Date;
import java.util.Set;

@Getter @Setter @NoArgsConstructor @AllArgsConstructor
public class PostsDTO extends BaseResponseDTO {

    private int postId;

    @NotBlank
    private String postTitle;

    @NotBlank
    private String postContent;

    private Date addedDate;

    private CategoryDTO category;

    private UserDTO user;

    private Set<CommentDTO> comments;

    private Set<String> images;
}
