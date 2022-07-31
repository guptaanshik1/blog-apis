package com.blogapis.blogapi.payload;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.Email;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotEmpty;
import java.util.HashSet;
import java.util.Set;

@Getter
@Setter
@NoArgsConstructor
public class UserDTO extends BaseResponseDTO {

    @NotEmpty
    private String name;

    @NotEmpty
    @Email(message = "Email address should be valid")
    private String email;

    @NotEmpty
    @Min(value = 6, message = "Password should be of atleast 6 characters")
    private String password;

    @NotEmpty
    private String about;

    private Set<CommentDTO> comments;

    private Set<RoleDTO> roles = new HashSet<>();
}