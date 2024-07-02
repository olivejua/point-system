package dev.olivejua.pointsystem.user.service;

import dev.olivejua.pointsystem.common.exception.DuplicateAttributeException;
import dev.olivejua.pointsystem.common.exception.NotFoundResourceException;
import dev.olivejua.pointsystem.common.service.ClockHolder;
import dev.olivejua.pointsystem.user.domain.User;
import dev.olivejua.pointsystem.user.domain.UserCreate;
import dev.olivejua.pointsystem.user.domain.UserUpdate;
import dev.olivejua.pointsystem.user.service.port.UserRepository;
import lombok.Builder;

@Builder
public class UserService {
    private final UserRepository userRepository;
    private final ClockHolder clockHolder;

    public User getById(long id) {
        return userRepository.findById(id)
                .orElseThrow(() -> new NotFoundResourceException("User"));
    }

    public User join(UserCreate userCreate) {
        boolean exists = userRepository.existsByEmail(userCreate.getEmail());
        if (exists) {
            throw new DuplicateAttributeException("유저의 이메일");
        }

        User user = User.from(userCreate, clockHolder);

        return userRepository.save(user);
    }

    public User login(String email) {
        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new NotFoundResourceException("User"));

        user = user.login(clockHolder);
        userRepository.save(user);

        return user;
    }

    public User update(long id, UserUpdate userUpdate) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new NotFoundResourceException("User"));

        user = user.update(userUpdate, clockHolder);

        return userRepository.save(user);
    }
}
