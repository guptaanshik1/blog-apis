package com.blogapis.blogapi.security;

import com.blogapis.blogapi.entity.User;
import com.blogapis.blogapi.exception.EmailNotFoundException;
import com.blogapis.blogapi.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class CustomUserDetailsService implements UserDetailsService {

    @Autowired
    private UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        // loading user from database through username
        User user = userRepository.findByEmail(username)
                .orElseThrow(() -> new EmailNotFoundException("User ", " email ", username));

        return user;
    }
}
