package com.blog.blogproject.dto;

import com.blog.blogproject.domain.Article;
import lombok.Data;
import lombok.Getter;

@Data
public class ArticleListViewResponse {

    //뷰에 데이터를 전달하기 위한 객체
    private final Long articleId;
    private final String articleTitle;
    private final String articleContent;

    public ArticleListViewResponse(Article article) {
        this.articleId = article.getArticleId();
        this.articleTitle = article.getArticleTitle();
        this.articleContent = article.getArticleContent();
    }
}
