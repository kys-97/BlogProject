package com.blog.blogproject.controller;

import com.blog.blogproject.domain.Article;
import com.blog.blogproject.dto.AddArticleRequest;
import com.blog.blogproject.dto.UpdateArticleRequest;
import com.blog.blogproject.repository.BlogRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;

@SpringBootTest //테스트용 어플리케이션 컨텍스트
@AutoConfigureMockMvc //MockMvc 생성 및 자동 구성
class BlogApiControllerTest {

    @Autowired
    protected MockMvc mockMvc;

    //직렬화, 역직렬화를 위한 클래스 주입
    @Autowired
    protected ObjectMapper objectMapper;
    @Autowired
    private WebApplicationContext context;

    @Autowired
    BlogRepository blogRepository;

    //테스트 실행 전 실행하는 메서드
    @BeforeEach
    public void mockMvcSetUp(){
        this.mockMvc = MockMvcBuilders.webAppContextSetup(context).build();
        blogRepository.deleteAll();
    }

    //given - when - then
    //블로그 글 추가에 필요한 요청 객체 생성
    //블로그 글 추가 api 요청을 보냄. 이때 요청 타입은 json이며 given에서 미리 만들어둔 객체를 요청 본문으로 함께 보냄
    //then 응답 코드가 201 created인지 확인. Blog를 전체 조회해 크기가 1인지 확인하고, 실제로 저장된 데이터와 요청 값을 비교
    @DisplayName("addArticle: 블로그 글 추가 성공")
    @Test
    public void addArticle() throws Exception{
        //given
        final String url = "/api/articles";
        final String articleTitle = "articleTitle";
        final String articleContent = "articleContent";
        final AddArticleRequest userRequest = new AddArticleRequest(articleTitle, articleContent);

        //객체 JSON으로 직렬화
        final String requestBody = objectMapper.writeValueAsString(userRequest);

        //when
        //설정한 내용을 바탕으로 요청 전송
        ResultActions result = mockMvc.perform(post(url)
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .content(requestBody));

        //then
        result.andExpect(status().isCreated());

        List<Article> articles = blogRepository.findAll();

        //크기가 1인지 검증
        assertThat(articles.size()).isEqualTo(1);
        assertThat(articles.get(0).getArticleTitle()).isEqualTo(articleTitle);
        assertThat(articles.get(0).getArticleContent()).isEqualTo(articleContent);
    }

    //글 조회 테스트
    @DisplayName("findAllArticles: 글 목록 조회 성공")
    @Test
    public void findAllArticles() throws Exception {
        //given: 블로그 글 저장
        final String url = "/api/articles";
        final String articleTitle = "articleTitle";
        final String articleContent = "articleContent";

        blogRepository.save(Article.builder().articleTitle(articleTitle).articleContent(articleContent).build());

        //when: 목록 조회 api 호출
        final ResultActions resultactions = mockMvc.perform(get(url).accept(MediaType.APPLICATION_JSON));
        //then: 응답 코드가 200 ok, 값 중 0번째 요소의 content와 title이 저장된 값과 같은지 확인
    }

    //글 번호 조회 테스트
    @DisplayName("findArticleById: 블로그 글 번호 조회 성공")
    @Test
    public  void findArticleById() throws Exception {
        //given: 블로그 글 저장
        final String url = "/api/articles/{articleId}";
        final String articleTitle = "articleTitle";
        final String articleContent = "articleContent";

        Article saveArticle = blogRepository.save(Article.builder().articleTitle(articleTitle).articleContent(articleContent).build());

        //when: 저장한 블로그 글의 id 값으로 api 호출
        final ResultActions resultActions = mockMvc.perform(get(url, saveArticle.getArticleId()));

        //then: 응답코드가 200, 반환받은 content와 title이 저장된 값과 같은지 확인
        resultActions
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.articleContent").value(articleContent))
                .andExpect(jsonPath("$.articleTitle").value(articleTitle));

    }

    //글 삭제 테스트
    @DisplayName("deleteArticle: 블로그 글 삭제 성공")
    @Test
    public void deleteArticle() throws Exception {
        //given: 블로그 글 저장
        final String url = "/api/articles/{articleId}";
        final String articleTitle = "articleTitle";
        final String articleContent = "articleContent";

        Article saveArticle = blogRepository.save(Article.builder().articleTitle(articleTitle).articleContent(articleContent).build());

        //when: 저장된 블로그 글의 id값으로 삭제 API 호출
        mockMvc.perform(delete(url, saveArticle.getArticleId())).andExpect(status().isOk());

        //then: 응답 코드 200ok, 블로그 글 리스트 전체 조회한 배열의 크기가 0인지 확인
        List<Article> articles = blogRepository.findAll();
        assertThat(articles).isEmpty();
    }

    @DisplayName("updateArticle: 블로그 글 수정 성공")
    @Test
    public void updateArticle() throws Exception {
        //given: 블로그 글 저장 후 블로그 글 수정에 필요한 요청 객체
        final String url = "/api/articles/{articleId}";
        final String articleTitle = "articleTitle";
        final String articleContent = "articleContent";

        Article saveArticle = blogRepository.save(Article.builder().articleTitle(articleTitle).articleContent(articleContent).build());

        final String newTitle = "new articleTitle";
        final String newContent = "new articleContent";

        UpdateArticleRequest request = new UpdateArticleRequest(newTitle, newContent);

        //when: update api로 수정 요청 보냄. 이때 요청 타입은 json이며, given에서 미리 만들어둔 객체를 요청 본문으로 함께 보냄
        ResultActions result = mockMvc.perform(put(url, saveArticle.getArticleId()).contentType(MediaType.APPLICATION_JSON_VALUE).content(objectMapper.writeValueAsString(request)));

        //then: 응답 코드가 200 ok인지 확인. 블로그 글 id로 조회한 후 값이 수정되었는지 확인
        result.andExpect(status().isOk());

        Article article = blogRepository.findById(saveArticle.getArticleId()).get();

        assertThat(article.getArticleTitle()).isEqualTo(newTitle);
        assertThat(article.getArticleContent()).isEqualTo(newContent);
    }

}