package com.blog.blogproject.service;

import com.blog.blogproject.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
//스프링 시큐리티에서 사용자 정보를 가져오는 인터페이스
public class UserDetailService implements UserDetailsService {
    //레파지토리 주입
    private final UserRepository userRepository;

    //email 로 사용자의 정보를 가져오는 메서드
    @Override
    public UserDetails loadUserByUsername(String userEmail) {
        return userRepository.findByEmail(userEmail)
                .orElseThrow(()-> new IllegalArgumentException((userEmail)));
    }


}
