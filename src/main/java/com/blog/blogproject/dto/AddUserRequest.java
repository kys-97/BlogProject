package com.blog.blogproject.dto;

import lombok.Data;

@Data
public class AddUserRequest {
    private String userEmail;
    private String userPassword;
}
