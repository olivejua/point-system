package dev.olivejua.pointsystem.service.impl;

import dev.olivejua.pointsystem.domain.User;
import dev.olivejua.pointsystem.exception.AlreadyExistsUsernameException;
import dev.olivejua.pointsystem.repository.UserRepository;
import dev.olivejua.pointsystem.service.UserService;
import dev.olivejua.pointsystem.web.dto.JoinRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class DefaultUserService implements UserService {
    private final UserRepository userRepository;

    @Override
    public Long join(JoinRequest request) {
        validate(request.getName());

        User user = User.create(request.getName());
        userRepository.save(user);

        return user.getId();
    }

    private void validate(String username) {
        boolean exists = userRepository.existsByName(username);
        if (exists) {
            throw new AlreadyExistsUsernameException(username);
        }
    }
}
