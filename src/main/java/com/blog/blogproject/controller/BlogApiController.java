package com.blog.blogproject.controller;

import com.blog.blogproject.domain.Article;
import com.blog.blogproject.dto.AddArticleRequest;
import com.blog.blogproject.dto.ArticleResponse;
import com.blog.blogproject.dto.UpdateArticleRequest;
import com.blog.blogproject.service.BlogService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@RestController //HTTP response body에 객체 데이터를 JSON 형식으로 변환
public class BlogApiController {

    private final BlogService blogService;

    //http method -> post
    //전달받은 url과 동일하면 메서드로 매핑
    @PostMapping("/api/articles")
    //@request body: 요청한 본문 값 매핑
    public ResponseEntity<Article> addArticle(@RequestBody AddArticleRequest request) {
        Article saveArticle = blogService.save(request);
        //요청한 자원이 성공적으로 생성되었으면 저장된 블로그 글 정보를 응답 객체에 담아 전송
        return  ResponseEntity.status(HttpStatus.CREATED).body(saveArticle);
    }

    //받은 요청을 서비스에 전달하는 컨트롤러
    //get 요청이 오면 글 목록을 조회하는 역할
    @GetMapping("/api/articles")
    public ResponseEntity<List<ArticleResponse>> findAllArticles() {
        List<ArticleResponse> articles = blogService.findAll()
                .stream()
                .map(ArticleResponse::new)
                .toList();

        return ResponseEntity.ok()
                .body(articles);
    }

    @GetMapping("/api/articles/{articleId}")
    //url에서 articleId에 해당하는 값이 파라미터로 지정된 articleId 값으로 들어옴
    public ResponseEntity<ArticleResponse> findArticle(@PathVariable long articleId) {
        Article article = blogService.findById(articleId);
        return ResponseEntity.ok().body(new ArticleResponse(article));
    }

    //delete
    // /api/article/{articleId} DELETE 요청이 오면 articleId에 해당하는 값이 @PathVariable 어노테이션을 통해 들어옴
    @DeleteMapping("/api/articles/{articleId}")
    public ResponseEntity<Void> deleteArticle (@PathVariable long articleId) {
        blogService.delete(articleId);
        return ResponseEntity.ok().build();
    }

    //update
    @PutMapping("/api/articles/{articleId}")
    public ResponseEntity<Article> updateArticle(@PathVariable long articleId, @RequestBody UpdateArticleRequest request) {
        Article updateArticle = blogService.update(articleId, request);
        return ResponseEntity.ok().body(updateArticle);

        // /api/articles/{articleId} PUT request
        // Request Body info -> request parameter
        // request, id -> service class update method
    }
}
