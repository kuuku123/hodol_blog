package com.tony.test_blog.request;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.validation.constraints.NotBlank;

@Setter
@Getter
@ToString
public class Login {

    @NotBlank(message = "type your email")
    private String email;
    @NotBlank(message = "type your password")
    private String password;
}
