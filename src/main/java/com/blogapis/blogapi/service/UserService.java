package com.blogapis.blogapi.service;

import com.blogapis.blogapi.payload.UserDTO;

import java.util.List;

public interface UserService {

    UserDTO registerNewUser(UserDTO userDTO);
    UserDTO createUser(UserDTO userDTO);
    UserDTO updateUser(UserDTO user, Integer userId);
    UserDTO getUserById(Integer userId);
    List<UserDTO> getAllUsers();
    void deleteUser(Integer userId);
}
