package com.blog.blogproject.service;

import lombok.RequiredArgsConstructor;
import com.blog.blogproject.domain.User;
import com.blog.blogproject.dto.AddUserRequest;
import com.blog.blogproject.repository.UserRepository;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class UserService {

    private final UserRepository userRepository;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;

    public void save(AddUserRequest dto) {
        userRepository.save(User.builder()
                .email(dto.getEmail())
                .password(bCryptPasswordEncoder.encode(dto.getPassword()))
                .build());
    }
}
