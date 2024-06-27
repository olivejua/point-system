package dev.olivejua.pointsystem.service;

import dev.olivejua.pointsystem.ApplicationTests;
import dev.olivejua.pointsystem.domain.UserEntity;
import dev.olivejua.pointsystem.repository.UserRepository;
import dev.olivejua.pointsystem.web.dto.JoinRequest;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

public class UserEntityServiceTests extends ApplicationTests {
    @Autowired
    private UserService userService;

    @Autowired
    private UserRepository userRepository;

    @Test
    void 유저가_가입한다() {
        JoinRequest request = new JoinRequest("신규유저1");

        Long savedUserId = userService.join(request);

        clearContext();

        Optional<UserEntity> optionalUser = userRepository.findById(savedUserId);
        assertThat(optionalUser).isPresent();
    }
}
