package com.tony.test_blog.request;

import com.tony.test_blog.exception.InvalidRequestException;
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

    public void validate() {
        if (title.contains("바보")) throw new InvalidRequestException("title", "제목에 바보를 포함할 수 없습니다.");
    }
}
