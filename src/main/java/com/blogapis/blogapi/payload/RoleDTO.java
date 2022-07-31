package com.blogapis.blogapi.payload;

import lombok.Data;

@Data
public class RoleDTO extends BaseResponseDTO {
    private int id;
    private String Name;
}
