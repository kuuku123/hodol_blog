package com.tony.test_blog.service;

import com.tony.test_blog.domain.User;
import com.tony.test_blog.exception.InvalidSignInInformationException;
import com.tony.test_blog.repository.UserRepository;
import com.tony.test_blog.request.Login;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthService {

    private final UserRepository userRepository;

    public void signIn(Login login) {
        User user = userRepository.findByEmailAndPassword(login.getEmail(), login.getPassword())
                .orElseThrow(InvalidSignInInformationException::new);
    }
}
