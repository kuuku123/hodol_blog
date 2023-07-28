package com.tony.test_blog.service;

import com.tony.test_blog.domain.User;
import com.tony.test_blog.exception.InvalidSignInInformationException;
import com.tony.test_blog.repository.UserRepository;
import com.tony.test_blog.request.Login;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.testcontainers.containers.JdbcDatabaseContainer;
import org.testcontainers.containers.MySQLContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class AuthServiceTest {

    @Autowired
    private AuthService authService;

    @Autowired
    private UserRepository userRepository;

    @Test
    @DisplayName("로그인시 비밀번호가 틀리면 InvalidSignInInformationException 을 던진다.")
    void test1() {
        //given
        Login login = new Login();
        login.setEmail("tony@gmail.com");
        login.setPassword("12345");

        //when

        //then
        InvalidSignInInformationException e = assertThrows(InvalidSignInInformationException.class, () -> {
            authService.signIn(login);
        });

        assertEquals("아이디/비밀번호가 올바르지 않습니다.", e.getMessage());
        assertEquals(400, e.getStatusCode());
    }
    @Test
    @DisplayName("로그인시 비밀번호가 맞으면 user를 리턴한다.")
    void test2() {
        //given
        Login login = new Login();
        login.setEmail("tony@gmail.com");
        login.setPassword("1234");

        //when

        //then
        Optional<User> byEmailAndPassword = userRepository.findByEmailAndPassword(login.getEmail(), login.getPassword());
        User user = byEmailAndPassword.get();
        System.out.println("user = " + user.getEmail());
        System.out.println("user = " + user.getPassword());
    }
}