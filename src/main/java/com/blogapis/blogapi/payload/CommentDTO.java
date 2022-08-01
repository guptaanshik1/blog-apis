package com.blogapis.blogapi.payload;

import lombok.*;

import javax.validation.constraints.NotBlank;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CommentDTO extends BaseResponseDTO {

    @NotBlank
    private String content;

}