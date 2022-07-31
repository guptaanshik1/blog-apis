package com.blogapis.blogapi.payload;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class PostResponseDTO extends BaseResponseDTO {
    private int postId;
    private List<PostsDTO> content;
    private int totalPages;
    private long totalElements;
    private int pageSize;
    private int pageNumber;
    private boolean firstPage;
    private boolean lastPage;
}