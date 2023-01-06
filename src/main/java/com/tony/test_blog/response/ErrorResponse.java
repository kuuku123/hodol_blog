package com.tony.test_blog.response;

import lombok.Builder;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.val;

import java.util.HashMap;
import java.util.Map;

/**
 *  {
 *      "code" : "400",
 *      "message" : "잘못된 요청입니다.",
 *      "validation": {
 *          "title" : "값을 입력해주셈."
 *      }
 *  }
 */
@Getter
@RequiredArgsConstructor
@Builder
public class ErrorResponse {

    private final String code;
    private final String message;
    private final Map<String,String> validation = new HashMap<>();

    public void addValidation(String fieldName, String errorMessage) {
        this.validation.put(fieldName,errorMessage);
    }

}
