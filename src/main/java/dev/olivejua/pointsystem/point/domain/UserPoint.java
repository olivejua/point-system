package dev.olivejua.pointsystem.point.domain;

import dev.olivejua.pointsystem.user.domain.User;

import java.time.LocalDateTime;

public class UserPoint {
    private long id;
    private User user;
    private long amount;
    private LocalDateTime createdAt;
    private LocalDateTime modifiedAt;
}
