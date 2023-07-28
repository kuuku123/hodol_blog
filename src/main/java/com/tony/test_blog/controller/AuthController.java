package com.tony.test_blog.controller;


import com.tony.test_blog.domain.User;
import com.tony.test_blog.exception.InvalidRequestException;
import com.tony.test_blog.exception.InvalidSignInInformationException;
import com.tony.test_blog.repository.UserRepository;
import com.tony.test_blog.request.Login;
import com.tony.test_blog.service.AuthService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequiredArgsConstructor
public class AuthController {

    private final AuthService authService;
    @PostMapping("/auth/login")
    public void login(@RequestBody Login login) {
        log.info(">>>>login={} ",login);
        authService.signIn(login);
    }
}
