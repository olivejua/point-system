package dev.olivejua.pointsystem.domain;

import jakarta.persistence.*;
import lombok.Getter;

@Getter
@Entity
public class Post extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "USER_ID")
    private User author;

    private String title;

    private String content;

    protected Post() {
    }

    public static Post create(User author, String title, String content) {
        Post post = new Post();
        post.author = author;
        post.title = title;
        post.content = content;

        return post;
    }
}
