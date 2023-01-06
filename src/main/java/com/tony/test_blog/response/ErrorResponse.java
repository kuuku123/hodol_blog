package com.tony.test_blog.response;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.*;

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
@JsonInclude(value = JsonInclude.Include.NON_EMPTY)
@Getter
@Builder
public class ErrorResponse {

    private final String code;
    private final String message;
    private final Map<String,String> validation;

    public ErrorResponse(String code, String message, Map<String, String> validation) {
        this.code = code;
        this.message = message;
        this.validation = validation != null? validation : new HashMap<>();
    }

    public void addValidation(String fieldName, String errorMessage) {
        this.validation.put(fieldName,errorMessage);
    }

}
