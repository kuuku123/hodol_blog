package com.tony.test_blog.repository;

import com.tony.test_blog.domain.Post;
import com.tony.test_blog.request.PostSearch;

import java.util.List;

public interface PostRepositoryCustom {

    List<Post> getList(PostSearch postSearch);
}
