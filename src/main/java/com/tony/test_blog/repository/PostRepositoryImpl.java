package com.tony.test_blog.repository;

import com.querydsl.jpa.impl.JPAQueryFactory;
import com.tony.test_blog.domain.Post;
import com.tony.test_blog.domain.QPost;
import com.tony.test_blog.request.PostSearch;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;


@RequiredArgsConstructor
@Repository
public class PostRepositoryImpl implements PostRepositoryCustom{

    private final JPAQueryFactory jpaQueryFactory;


    @Override
    public List<Post> getList(PostSearch postSearch) {
        List<Post> fetch = jpaQueryFactory.selectFrom(QPost.post)
                .limit(postSearch.getSize())
                .offset(postSearch.getOffset())
                .orderBy(QPost.post.id.desc())
                .fetch();
        return fetch;
    }
}
