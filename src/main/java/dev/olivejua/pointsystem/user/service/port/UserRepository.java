package dev.olivejua.pointsystem.user.service.port;

import dev.olivejua.pointsystem.user.domain.User;

import java.util.Optional;

public interface UserRepository {

    User getById(long id);

    Optional<User> findById(long id);

    Optional<User> findByEmail(String email);

    boolean existsByEmail(String email);

    User save(User user);

}
