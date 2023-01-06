package com.tony.test_blog.service;

import com.tony.test_blog.domain.Post;
import com.tony.test_blog.repository.PostRepository;
import com.tony.test_blog.request.PostCreate;
import com.tony.test_blog.request.PostEdit;
import com.tony.test_blog.request.PostSearch;
import com.tony.test_blog.response.PostResponse;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class PostServiceTest {

    @Autowired
    private PostService postService;

    @Autowired
    private PostRepository postRepository;

    @BeforeEach
    void clean() {
        postRepository.deleteAll();
    }

    @Test
    @DisplayName("글 작성")
    void test1() {
        // given

        PostCreate postCreate = PostCreate.builder()
                .title("제목입니다.")
                .content("내용입니다.")
                .build();

        // when

        postService.write(postCreate);
        // then

        Assertions.assertEquals(1L, postRepository.count());
        Post post = postRepository.findAll().get(0);
        assertEquals("제목입니다.",post.getTitle());
        assertEquals("내용입니다.",post.getContent());
    }

    @Test
    @DisplayName("글 1개 조회")
    void test2() {
        //given

        Post requestPost = Post.builder()
                .title("foo")
                .content("bar")
                .build();

        postRepository.save(requestPost);
        Long postId = 1l;

        //when
        PostResponse post = postService.get(requestPost.getId());

        //then
        assertNotNull(post);
        assertEquals(1L,postRepository.count());
        assertEquals("foo",post.getTitle());
        assertEquals("bar",post.getContent());
    }


    @Test
    @DisplayName("글 여러개 조회")
    void test3() {

        //given
        List<Post> requestPosts = IntStream.range(0,20)
                        .mapToObj(i ->
                            Post.builder()
                                    .title("title " + i)
                                    .content("content " + i)
                                    .build()
                        ).collect(Collectors.toList());
        postRepository.saveAll(requestPosts);


        PostSearch postSearch = PostSearch.builder()
                .page(1)
                .size(10)
                .build();
        //when
        List<PostResponse> posts = postService.getList(postSearch);


        //then
        assertEquals(10L,posts.size());
        assertEquals("title 19",posts.get(0).getTitle());
    }


    @Test
    @DisplayName("글 제목 수정")
    void test4() {

        //given
        Post post = Post.builder()
                .title("title")
                .content("content")
                .build();
        postRepository.save(post);

        PostEdit postEdit = PostEdit.builder()
                .title("title2")
                .content("content")
                .build();

        //when
        postService.edit(post.getId(),postEdit);

        //then

        Post changedPost = postRepository.findById(post.getId())
                .orElseThrow(() -> new RuntimeException("글이 존재하지 않습니다. id=" + post.getId()));
        assertEquals(changedPost.getTitle(),"title2");
        assertEquals(changedPost.getContent(),"content");
    }


    @Test
    @DisplayName("글 내용 수정")
    void test5() {

        //given
        Post post = Post.builder()
                .title("title")
                .content("content")
                .build();
        postRepository.save(post);

        PostEdit postEdit = PostEdit.builder()
                .title("title")
                .content("content2")
                .build();

        //when
        postService.edit(post.getId(),postEdit);

        //then

        Post changedPost = postRepository.findById(post.getId())
                .orElseThrow(() -> new RuntimeException("글이 존재하지 않습니다. id=" + post.getId()));
        assertEquals(changedPost.getTitle(),"title");
        assertEquals(changedPost.getContent(),"content2");
    }

    @Test
    @DisplayName("글 게시글 삭제")
    void test6() {

        //given
        Post post = Post.builder()
                .title("title")
                .content("content")
                .build();
        postRepository.save(post);

        //when

        postService.delete(post.getId());

        //then

        assertEquals(0,postRepository.count());
    }

}