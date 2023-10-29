package com.blog.blogproject.dto;

import com.blog.blogproject.domain.Article;
import lombok.Getter;

//응답을 위한 dto
@Getter
public class ArticleResponse {

    private final String articleTitle;
    private final String articleContent;

    //엔티티를 인수로 받는 생성자 추가
    public ArticleResponse(Article article) {
        this.articleTitle = article.getArticleTitle();
        this.articleContent = article.getArticleContent();
    }
}
