package dev.olivejua.pointsystem.user.domain;

import dev.olivejua.pointsystem.common.exception.InvalidAttributeFormatException;
import dev.olivejua.pointsystem.common.service.ClockHolder;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class User {
    private final Long id;
    private final String email;
    private final String nickname;
    private final UserStatus status;
    private final long createdAt;
    private final long modifiedAt;
    private final long lastLoginAt;

    public static User from(UserCreate userCreate, ClockHolder clockHolder) {
        if (userCreate.hasInvalidEmail() || userCreate.hasInvalidNickname()) {
            throw new InvalidAttributeFormatException("유저의 이메일 또는 닉네임");
        }

        long createdAt = clockHolder.millis();

        return User.builder()
                .email(userCreate.getEmail())
                .nickname(userCreate.getNickname())
                .status(UserStatus.ACTIVE)
                .createdAt(createdAt)
                .modifiedAt(createdAt)
                .build();
    }

    public User login(ClockHolder clockHolder) {
        return User.builder()
                .id(id)
                .email(email)
                .nickname(nickname)
                .status(status)
                .createdAt(createdAt)
                .modifiedAt(modifiedAt)
                .lastLoginAt(clockHolder.millis())
                .build();
    }

    public User update(UserUpdate userUpdate, ClockHolder clockHolder) {
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
                .modifiedAt(clockHolder.millis())
                .lastLoginAt(lastLoginAt)
                .build();
    }

    public boolean isSameAs(User user) {
        return id != null && id.equals(user.id);
    }
}
