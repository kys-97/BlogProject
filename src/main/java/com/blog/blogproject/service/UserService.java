package com.blog.blogproject.service;

import com.blog.blogproject.domain.User;
import com.blog.blogproject.dto.AddUserRequest;
import com.blog.blogproject.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class UserService {

    private final UserRepository userRepository;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;

    public Long save(AddUserRequest dto) {
        return userRepository.save(User.builder()
                .userEmail(dto.getUserEmail()).userPassword(bCryptPasswordEncoder.encode(dto.getUserPassword())).build()).getUserId();
    }
}
