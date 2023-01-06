package com.tony.test_blog.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.tony.test_blog.domain.Post;
import com.tony.test_blog.repository.PostRepository;
import com.tony.test_blog.request.PostCreate;
import com.tony.test_blog.request.PostEdit;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.net.URL;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;

@SpringBootTest
@AutoConfigureMockMvc
@PropertySource("classpath:messages.properties")
class PostControllerTest {

    @Autowired
    private ObjectMapper objectMapper;
    @Autowired
    private Environment environment;
    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private PostRepository postRepository;


    @BeforeEach
    void clean() {
        postRepository.deleteAll();
    }

    @Test
    @DisplayName("/posts 요청시 Hello World를 출력한다.")
    void test() throws Exception {

        // given
        PostCreate request = PostCreate.builder()
                .title("제목입니다.")
                .content("내용입니다.")
                .build();

        String json = objectMapper.writeValueAsString(request);

        // expected
        mockMvc.perform(MockMvcRequestBuilders.post("/posts")
                        .contentType(MediaType.APPLICATION_JSON)
                                .content(json)
//                        .content("{\"title\": \"hithere\", \"content\": \"blah\"}")
                )
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().string(""))
                .andDo(print());

    }

    @Test
    @DisplayName("/posts 요청시 title 값은 필수다.")
    void test2() throws Exception {

        Class<? extends PostControllerTest> aClass = getClass();
        ClassLoader classLoader = getClass().getClassLoader();
        URL resource = classLoader.getResource("messages.properties");


        // given
        PostCreate request = PostCreate.builder()
                .content("내용입니다.")
                .build();

        String json = objectMapper.writeValueAsString(request);
        // expected
        mockMvc.perform(MockMvcRequestBuilders.post("/posts")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(json)
                )
                .andExpect(MockMvcResultMatchers.status().isBadRequest())
                .andExpect(MockMvcResultMatchers.jsonPath("$.code").value("400"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.message").value("잘못된 요청입니다."))
                .andExpect(MockMvcResultMatchers.jsonPath("$.validation.title").value(environment.getProperty("post.NotBlank")))
                .andDo(print());
    }


    @Test
    @DisplayName("/posts 요청시 db에 값이 저장된다.")
    void test3() throws Exception {

        // given
        PostCreate request = PostCreate.builder()
                .title("제목입니다.")
                .content("내용입니다.")
                .build();

        String json = objectMapper.writeValueAsString(request);
        // expected
        mockMvc.perform(MockMvcRequestBuilders.post("/posts")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(json)
                )
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andDo(print());

        // then
        assertEquals(1L, postRepository.count());

        Post post = postRepository.findAll().get(0);
        assertEquals("제목입니다.",post.getTitle());
        assertEquals("내용입니다.",post.getContent());
    }

    @Test
    @DisplayName("글 1개 조회")
    void test4() throws Exception {
        // given
        Post post = Post.builder()
                .title("123456789012345")
                .content("bar")
                .build();
        postRepository.save(post);
        // when

        // expected
        mockMvc.perform(MockMvcRequestBuilders.get("/posts/{postId}",post.getId())
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.id").value(post.getId()))
                .andExpect(MockMvcResultMatchers.jsonPath("$.title").value("1234567890"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.content").value("bar"))
                .andDo(print());

        // then
    }


    @Test
    @DisplayName("글 여러개 조회")
    void test5() throws Exception {
        // given

        List<Post> requestPosts = IntStream.range(0,20)
                .mapToObj(i ->
                        Post.builder()
                                .title("title " + i)
                                .content("content " + i)
                                .build()
                ).collect(Collectors.toList());
        postRepository.saveAll(requestPosts);

        // expected
        mockMvc.perform(MockMvcRequestBuilders.get("/posts?page=1&size=10&sort=id,desc")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.length()",Matchers.is(10)))
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].title").value("title 19"))
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].content").value("content 19"))
                .andDo(print());
    }

    @Test
    @DisplayName("페이지를 0으로 요청하면 첫 페이지를 가져온다.")
    void test6() throws Exception {
        // given

        List<Post> requestPosts = IntStream.range(0,20)
                .mapToObj(i ->
                        Post.builder()
                                .title("title " + i)
                                .content("content " + i)
                                .build()
                ).collect(Collectors.toList());
        postRepository.saveAll(requestPosts);

        // expected
        mockMvc.perform(MockMvcRequestBuilders.get("/posts?page=0&size=10&sort=id,desc")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.length()",Matchers.is(10)))
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].title").value("title 19"))
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].content").value("content 19"))
                .andDo(print());
    }


    @Test
    @DisplayName("글 제목 수정.")
    void test7() throws Exception {
        // given

        Post post = Post.builder()
                .title("title")
                .content("content")
                .build();
        postRepository.save(post);

        PostEdit postEdit = PostEdit.builder()
                .title("title2")
                .content("content")
                .build();

        // expected
        mockMvc.perform(MockMvcRequestBuilders.patch("/posts/{postId}",post.getId())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(postEdit)))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.title").value("title2"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.content").value("content"))
                .andDo(print());
    }

    @Test
    @DisplayName("글 게시글 삭제")
    void test8() throws Exception {
        // given

        Post post = Post.builder()
                .title("title")
                .content("content")
                .build();
        postRepository.save(post);

        // expected
        mockMvc.perform(MockMvcRequestBuilders.delete("/posts/{postId}",post.getId())
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andDo(print());
    }
}