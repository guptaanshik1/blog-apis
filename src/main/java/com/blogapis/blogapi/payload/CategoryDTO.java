package com.blogapis.blogapi.payload;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

@NoArgsConstructor @AllArgsConstructor @Getter @Setter
public class CategoryDTO extends BaseResponseDTO {
    private int categoryId;

    @NotBlank
    @Size(min = 4, message = "Title should be more than 4 characters.")
    private String categoryTitle;

    @NotBlank
    @Size(min = 10, message = "Description should be more than 10 characters.")
    private String categoryDescription;
}