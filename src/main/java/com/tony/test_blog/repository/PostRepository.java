package com.tony.test_blog.repository;

import com.tony.test_blog.domain.Post;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PostRepository extends JpaRepository<Post, Long> ,PostRepositoryCustom {
}
