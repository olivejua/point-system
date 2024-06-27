package dev.olivejua.pointsystem.user.domain;

import java.time.LocalDateTime;

public class User {
    private Long id;
    private String email;
    private String nickname;
    private UserStatus status;
    private LocalDateTime createdAt;
    private LocalDateTime modifiedAt;
}
