package dev.olivejua.pointsystem.user.service;

import dev.olivejua.pointsystem.common.exception.DuplicateAttributeException;
import dev.olivejua.pointsystem.common.exception.NotFoundResourceException;
import dev.olivejua.pointsystem.common.service.DateTimeHolder;
import dev.olivejua.pointsystem.user.domain.User;
import dev.olivejua.pointsystem.user.domain.UserCreate;
import dev.olivejua.pointsystem.user.domain.UserUpdate;
import dev.olivejua.pointsystem.user.service.port.UserRepository;
import lombok.Builder;

@Builder
public class UserService {
    private final UserRepository userRepository;
    private final DateTimeHolder dateTimeHolder;

    public User getById(long id) {
        return userRepository.findById(id)
                .orElseThrow(() -> new NotFoundResourceException("User"));
    }

    public User join(UserCreate userCreate) {
        boolean exists = userRepository.existsByEmail(userCreate.getEmail());
        if (exists) {
            throw new DuplicateAttributeException("유저의 이메일");
        }

        User user = User.from(userCreate, dateTimeHolder);

        return userRepository.save(user);
    }

    public void login(String email) {
        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new NotFoundResourceException("User"));

        user = user.login(dateTimeHolder);
        userRepository.save(user);

        //TODO 출석체크 적립 대상이면 적립한다
    }

    public User update(long id, UserUpdate userUpdate) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new NotFoundResourceException("User"));

        user = user.update(userUpdate, dateTimeHolder);

        return userRepository.save(user);
    }
}
