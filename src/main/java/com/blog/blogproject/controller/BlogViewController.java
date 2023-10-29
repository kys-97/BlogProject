package com.blog.blogproject.controller;

import com.blog.blogproject.domain.Article;
import com.blog.blogproject.dto.ArticleListViewResponse;
import com.blog.blogproject.dto.ArticleViewResponse;
import com.blog.blogproject.service.BlogService;
import jakarta.persistence.Entity;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

//블로그 글 전체 리스트를 담을 뷰 반환
@Controller
@RequiredArgsConstructor
public class BlogViewController {

    private final BlogService blogService;

    @GetMapping("/articles")
    public String getArticles(Model model) {
        List<ArticleListViewResponse> articles = blogService.findAll()
                .stream()
                .map(ArticleListViewResponse::new)
                .toList();

        model.addAttribute("articles",articles); //model에 블로그 글 리스트 저장
        return "articleList"; //articleList.html 조회
    }

    //블로그 글을 반환할 컨트롤러 메서드
    @GetMapping("/articles/{articleId}")
    public String getArticle(@PathVariable Long articleId, Model model) {
        Article article = blogService.findById(articleId);
        model.addAttribute("article", new ArticleViewResponse(article));
        return "article";
    }

    @GetMapping("/new-article")
    //id 키를 가진 쿼리 파라미터의 값을 id 변수에 매핑 (id는 없을 수도 있음)
    public String newArticle(@RequestParam(required = false) Long articleId, Model model) {
        if (articleId == null) {
            //id가 없으면 생성
            model.addAttribute("article", new ArticleViewResponse());
        }
        else {
            //id가 있으면 수정
            Article article = blogService.findById(articleId);
            model.addAttribute("article", new ArticleViewResponse(article));
        }
        return "newArticle";
    }

}
