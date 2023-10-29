package com.blog.blogproject.dto;

import com.blog.blogproject.domain.Article;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
@Builder
public class AddArticleRequest {
    private String articleTitle;
    private String articleContent;

    public Article toEntity() {
        return Article.builder().
                articleTitle(articleTitle).
                articleContent(articleContent).
                build();
    }
}
