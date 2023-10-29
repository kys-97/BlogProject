package com.blog.blogproject;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@SpringBootApplication
@EnableJpaAuditing //createdAt, updatedAt 자동 업데이트
public class BlogProjectApplication {
	public static void main(String[] args) {

		SpringApplication.run(BlogProjectApplication.class, args);
	}

}
