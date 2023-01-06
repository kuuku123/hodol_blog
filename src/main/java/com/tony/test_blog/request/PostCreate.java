package com.tony.test_blog.request;

import lombok.*;
import org.springframework.beans.factory.annotation.Value;

import javax.validation.constraints.NotBlank;

@Setter
@Getter
@ToString
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class PostCreate {


    @NotBlank(message = "{post.NotBlank}")
    private String title;

    @NotBlank(message = "컨텐트를 입력해")
    private String content;

}
