package com.tony.test_blog.repository;

import com.tony.test_blog.domain.User;
import org.springframework.data.repository.CrudRepository;

import java.util.Optional;

public interface UserRepository extends CrudRepository<User,Long> {

    Optional<User> findByEmailAndPassword(String email, String password);
}
