package dev.olivejua.pointsystem.service;

import dev.olivejua.pointsystem.web.dto.JoinRequest;

public interface UserService {
    Long join(JoinRequest request);
}
