package com.blogapis.blogapi.payload;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import javax.validation.constraints.NotBlank;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class RepliesDTO extends BaseResponseDTO {

    @NotBlank
    private String content;

    private UserDTO user;

    private CommentDTO comment;

}