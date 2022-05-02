package dev.olivejua.pointsystem.repository;

import dev.olivejua.pointsystem.ApplicationTests;
import dev.olivejua.pointsystem.domain.Post;
import dev.olivejua.pointsystem.domain.User;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

public class PostRepositoryTest extends ApplicationTests {
    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PostRepository postRepository;

    @Test
    void 게시글이_저장된다() {
        //given
        String title = "나는 홍길동";
        Post post = Post.create(saveAuthor(), title, "길똥아");

        //when
        postRepository.save(post);

        clearContext();

        //then
        Optional<Post> postOptional = postRepository.findById(post.getId());
        assertThat(postOptional).isPresent();

        Post findPost = postOptional.get();
        assertThat(findPost.getTitle()).isEqualTo(title);
    }

    private User saveAuthor() {
        User author = User.create("Author");
        return userRepository.save(author);
    }
}
