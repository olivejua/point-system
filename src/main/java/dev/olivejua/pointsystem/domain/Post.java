package dev.olivejua.pointsystem.domain;

import javax.persistence.*;

import static javax.persistence.FetchType.LAZY;
import static javax.persistence.GenerationType.IDENTITY;

@Entity
public class Post extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = IDENTITY)
    private Long id;

    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "USER_ID")
    private User author;

    private String title;

    private String content;

    protected Post() {
    }

    protected static Post create(User author, String title, String content) {
        Post post = new Post();
        post.author = author;
        post.title = title;
        post.content = content;

        return post;
    }
}
