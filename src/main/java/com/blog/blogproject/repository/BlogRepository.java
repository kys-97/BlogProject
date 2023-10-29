package com.blog.blogproject.repository;

import com.blog.blogproject.domain.Article;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BlogRepository extends JpaRepository<Article, Long> {
}
