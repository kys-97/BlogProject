package com.blog.blogproject.dto;

import com.blog.blogproject.domain.Article;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
public class ArticleViewResponse {

    private Long articleId;
    private String articleTitle;
    private String articleContent;
    private LocalDateTime createdAt;

    public ArticleViewResponse(Article article) {
        this.articleId = article.getArticleId();
        this.articleTitle = article.getArticleTitle();
        this.articleContent = article.getArticleContent();
        this.createdAt = article.getCreatedAt();
    }

}
