package com.blogapis.blogapi.payload;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ImageDTO extends BaseResponseDTO {

    private String fileName;

    private PostsDTO postsDTO;

}
