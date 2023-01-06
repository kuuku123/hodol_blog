package com.tony.test_blog;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Pojo {

    @Builder.Default
    private String name = "s";
    @Builder.Default
    private boolean original = true;
}