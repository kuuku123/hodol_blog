package com.tony.test_blog;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class TestBlogApplicationTests {

    @Test
    void contextLoads() {
    }
    @Test
    public void givenBuilderWithDefaultValue_NoArgsWorksAlso() {
        Pojo build = Pojo.builder()
                .build();
        Pojo pojo = new Pojo();
        Assertions.assertEquals(build.getName(), pojo.getName());
        Assertions.assertTrue(build.isOriginal() == pojo.isOriginal());
        System.out.println();
    }
}
