package dev.olivejua.pointsystem.user.domain;

import dev.olivejua.pointsystem.common.exception.InvalidAttributeFormatException;
import dev.olivejua.pointsystem.common.service.DateTimeHolder;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
@Builder
public class User {
    private final Long id;
    private final String email;
    private final String nickname;
    private final UserStatus status;
    private final LocalDateTime createdAt;
    private final LocalDateTime modifiedAt;
    private final LocalDateTime lastLoginAt;

    public static User from(UserCreate userCreate, DateTimeHolder dateTimeHolder) {
        if (userCreate.hasInvalidEmail() || userCreate.hasInvalidNickname()) {
            throw new InvalidAttributeFormatException("유저의 이메일 또는 닉네임");
        }

        LocalDateTime createdAt = dateTimeHolder.now();

        return User.builder()
                .email(userCreate.getEmail())
                .nickname(userCreate.getNickname())
                .status(UserStatus.ACTIVE)
                .createdAt(createdAt)
                .modifiedAt(createdAt)
                .build();
    }

    public User login(DateTimeHolder dateTimeHolder) {
        return User.builder()
                .id(id)
                .email(email)
                .nickname(nickname)
                .status(status)
                .createdAt(createdAt)
                .modifiedAt(modifiedAt)
                .lastLoginAt(dateTimeHolder.now())
                .build();
    }

    public User update(UserUpdate userUpdate, DateTimeHolder dateTimeHolder) {
        if (userUpdate.hasSameEmailAndNicknameFrom(this)) {
            return this;
        }

        if (userUpdate.hasInvalidEmail() || userUpdate.hasInvalidNickname()) {
            throw new InvalidAttributeFormatException("유저의 이메일 또는 닉네임");
        }

        return User.builder()
                .id(id)
                .email(userUpdate.getEmail())
                .nickname(userUpdate.getNickname())
                .status(status)
                .createdAt(createdAt)
                .modifiedAt(dateTimeHolder.now())
                .lastLoginAt(lastLoginAt)
                .build();
    }
}
