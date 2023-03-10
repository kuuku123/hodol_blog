package com.tony.test_blog.service;

import com.tony.test_blog.domain.Post;
import com.tony.test_blog.domain.PostEditor;
import com.tony.test_blog.exception.PostNotFoundException;
import com.tony.test_blog.repository.PostRepository;
import com.tony.test_blog.request.PostCreate;
import com.tony.test_blog.request.PostEdit;
import com.tony.test_blog.request.PostSearch;
import com.tony.test_blog.response.PostResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Service
@RequiredArgsConstructor
public class PostService {

    private final PostRepository postRepository;

    public void write(PostCreate postCreate) {

        Post post = Post.builder()
                .title(postCreate.getTitle())
                .content(postCreate.getContent())
                .build();
        postRepository.save(post);
    }

    public PostResponse get(Long id) {
        Post post = postRepository.findById(id)
                .orElseThrow(PostNotFoundException::new);

        PostResponse response = PostResponse.builder()
                .id(post.getId())
                .title(post.getTitle())
                .content(post.getContent())
                .build();

        return response;
    }

    public List<PostResponse> getList(PostSearch postSearch) {

        List<PostResponse> all = postRepository.getList(postSearch ).stream()
                .map(PostResponse::new)
                .collect(Collectors.toList());
        return all;
    }

    @Transactional
    public PostResponse edit(Long id , PostEdit postEdit) {
        Post post = findPostById(id);

        PostEditor.PostEditorBuilder postEditorBuilder = post.toEditor();

        PostEditor postEditor = postEditorBuilder.title(postEdit.getTitle())
                .content(postEdit.getContent())
                .build();
        post.edit(postEditor);

        return new PostResponse(post);

    }

    public void delete(Long id) {
        Post post = findPostById(id);
        postRepository.delete(post);
    }


    private Post findPostById(Long id) throws PostNotFoundException {
        Post post = postRepository.findById(id)
                .orElseThrow(PostNotFoundException::new);
        return post;
    }
}
