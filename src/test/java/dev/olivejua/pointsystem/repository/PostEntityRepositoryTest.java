package dev.olivejua.pointsystem.repository;

import dev.olivejua.pointsystem.ApplicationTests;
import dev.olivejua.pointsystem.domain.PostEntity;
import dev.olivejua.pointsystem.domain.UserEntity;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

public class PostEntityRepositoryTest extends ApplicationTests {
    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PostRepository postRepository;

    @Test
    void 게시글이_저장된다() {
        //given
        String title = "나는 홍길동";
        PostEntity postEntity = PostEntity.create(saveAuthor(), title, "길똥아");

        //when
        postRepository.save(postEntity);

        clearContext();

        //then
        Optional<PostEntity> postOptional = postRepository.findById(postEntity.getId());
        assertThat(postOptional).isPresent();

        PostEntity findPostEntity = postOptional.get();
        assertThat(findPostEntity.getTitle()).isEqualTo(title);
    }

    private UserEntity saveAuthor() {
        UserEntity author = UserEntity.create("Author");
        return userRepository.save(author);
    }
}
