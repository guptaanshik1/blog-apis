package com.blogapis.blogapi.service.impl;

import com.blogapis.blogapi.entity.Role;
import com.blogapis.blogapi.entity.User;
import com.blogapis.blogapi.exception.ResourceNotFoundException;
import com.blogapis.blogapi.payload.UserDTO;
import com.blogapis.blogapi.repository.RoleRepository;
import com.blogapis.blogapi.repository.UserRepository;
import com.blogapis.blogapi.service.UserService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private RoleRepository roleRepository;

    @Autowired
    private ModelMapper modelMapper;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Override
    public UserDTO registerNewUser(UserDTO userDTO) {
        User user = dtoToUser(userDTO);
        System.out.println(userDTO.getPassword());
        System.out.println(user.getPassword());
        user.setPassword(passwordEncoder.encode(userDTO.getPassword()));
        Optional<Role> r = roleRepository.findById(502); // 502 for normal user
        Role role = r.get();
        System.out.println("Line 42: " + user.getRoles());
        user.getRoles().add(role);
        System.out.println("Line 44: " + user.getRoles());
        User createdUser = userRepository.save(user);
        return userToDto(createdUser);
    }

    @Override
    public UserDTO createUser(UserDTO userDTO) {
        User user = dtoToUser(userDTO);
        User savedUser = userRepository.save(user);
        return userToDto(savedUser);
    }

    @Override
    public UserDTO updateUser(UserDTO userDTO, Integer userId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new ResourceNotFoundException("User", " Id ", userId));

        user.setName(userDTO.getName());
        user.setEmail(userDTO.getEmail());
        user.setPassword(userDTO.getPassword());
        user.setAbout(userDTO.getAbout());

        User updatedUser = userRepository.save(user);
        return userToDto(updatedUser);
    }

    @Override
    public UserDTO getUserById(Integer userId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new ResourceNotFoundException("User", " Id ", userId));

        return userToDto(user);
    }

    @Override
    public List<UserDTO> getAllUsers() {
        List<User> users = userRepository.findAll();
        List<UserDTO> userDTOS = users.stream().map(user -> userToDto(user)).collect(Collectors.toList());
        return userDTOS;
    }

    @Override
    public void deleteUser(Integer userId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new ResourceNotFoundException("User", " Id ", userId));

        userRepository.delete(user);
    }

    private User dtoToUser(UserDTO userDTO) {
        User user = modelMapper.map(userDTO, User.class);
        return user;
    }

    private UserDTO userToDto(User user) {
        UserDTO userDTO = modelMapper.map(user, UserDTO.class);
        return userDTO;
    }
}