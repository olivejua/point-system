package dev.olivejua.pointsystem.repository;

import dev.olivejua.pointsystem.ApplicationTests;
import dev.olivejua.pointsystem.domain.User;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import static org.assertj.core.api.Assertions.assertThat;

public class UserRepositoryTest extends ApplicationTests {
    @Autowired
    private UserRepository userRepository;

    @Test
    void 유저가_저장된다() {
        //given
        User user = User.create("User-A");

        //when
        userRepository.save(user);

        clearContext();

        //then
        assertThat(userRepository.findById(user.getId())).isPresent();
    }
}
