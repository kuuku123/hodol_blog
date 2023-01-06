package com.tony.test_blog.domain;

import com.tony.test_blog.request.PostEdit;
import lombok.*;

import javax.persistence.*;

@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
@Builder
@AllArgsConstructor
public class Post {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String title;

    @Lob
    private String content;

    public Post(String title, String content) {

        this.title = title;
        this.content = content;
    }

    public PostEditor.PostEditorBuilder toEditor() {
        PostEditor.PostEditorBuilder builder = PostEditor.builder()
                .title(title)
                .content(content);
        return builder;
    }

    public void edit(PostEditor postEditor) {
        title = postEditor.getTitle();
        content = postEditor.getContent();
    }

}
