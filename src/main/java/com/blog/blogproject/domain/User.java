package com.blog.blogproject.domain;

import jakarta.persistence.*;
import lombok.*;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.switchuser.SwitchUserGrantedAuthority;

import java.util.Collection;
import java.util.List;

@Entity
@Data
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Table(name = "users")
@Builder
public class User implements UserDetails {
    //UserDetails를 상속받아 인증 객체로 사용

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(updatable = false)
    private Long userId;
    @Column(nullable = false, unique = true)
    private String userEmail;
    private String userPassword;

    @Builder
    public User(String userEmail, String userPassword, String auth) {
        this.userEmail = userEmail;
        this.userPassword = userPassword;
    }


    //권한 반환
    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return List.of(new SimpleGrantedAuthority("user"));
    }

    //사용자의 패스워드 반환
    @Override
    public String getPassword() {
        return userPassword;
    }

    //사용자의 id 반환 (고유값)
    @Override
    public String getUsername() {
        return userEmail;
    }

    //계정 만료 여부 반환
    @Override
    public boolean isAccountNonExpired() {
        //만료되었는지 확인하는 로직
        return true; //true: 만료 x
    }

    //계정 잠금 여부 반환
    @Override
    public boolean isAccountNonLocked() {
        return true; //계정 잠금 확인 로직. true: 잠금 x
    }

    //패스워드 만료 여부 반환
    @Override
    public boolean isCredentialsNonExpired() {
        //패스워드가 만료되었는지 확인하는 로직
        return true; //true: 만료 x
    }

    //계정 사용 가능 여부 반환
    @Override
    public boolean isEnabled() {
        //계정이 사용 가능한지 확인
        return true; //true: 사용 가능
    }
}
