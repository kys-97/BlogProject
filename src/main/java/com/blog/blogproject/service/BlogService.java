package com.blog.blogproject.service;

import com.blog.blogproject.domain.Article;
import com.blog.blogproject.dto.AddArticleRequest;
import com.blog.blogproject.dto.UpdateArticleRequest;
import com.blog.blogproject.repository.BlogRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@RequiredArgsConstructor //빈을 생성자로 생성하는 롬복에서 지원하는 어노테이션. final, @NotNull이 붙은 필드로 생성자 만들어줌
@Service  //해당 클래스를 빈으로 서블릿 컨테이너에 등록해줌
public class BlogService {
    
    private final BlogRepository blogRepository;
    
    //블로그 글 추가 메서드
    public Article save(AddArticleRequest request) {
        //save()는 jpa에서 지원하는 저장 메서드
        //AddArtcleRequest 클래스에 저장된 값들(dto)을 article 데이터베이스에 저장
        return blogRepository.save(request.toEntity());
    }

    //블로그 글 목록 조회
    public List<Article> findAll() {
        //JPA method -> findAll()을 통해 article 테이블에 저장되어 있는 모든 데이터 조회
        return blogRepository.findAll();
    }

    //블로그 글 목록 id로 조회
    public Article findById(long articleId) {
        //JPA에서 제공하는 findById를 사용해 Id를 받아 엔티티를 조회하고 없으면 예외 발생
        return blogRepository.findById(articleId).orElseThrow(()->new IllegalArgumentException("not found: "+articleId));
    }

    //블로그 글 삭제
    public void delete(long articleId) {
        blogRepository.deleteById(articleId);
    }

    //블로그 글 수정
    //트랜잭션 메서드
    @Transactional
    public Article update(long articleId, UpdateArticleRequest request) {
        Article article = blogRepository.findById(articleId).orElseThrow(()->new IllegalArgumentException("Not found: "+articleId));

        article.update(request.getArticleTitle(), request.getArticleContent());
        return article;

        //@Transactional
        //매칭한 메서드를 하나의 트랜잭션으로 묶는 역할
    }



}
