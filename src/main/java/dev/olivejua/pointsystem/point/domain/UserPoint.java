package dev.olivejua.pointsystem.point.domain;

import dev.olivejua.pointsystem.common.service.ClockHolder;
import dev.olivejua.pointsystem.user.domain.User;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class UserPoint {
    private final Long id;
    private final User user;
    private final long amount;
    private final long createdAt;
    private final long modifiedAt;

    public static UserPoint initialFrom(User user, ClockHolder clockHolder) {
        long now = clockHolder.millis();

        return UserPoint.builder()
                .user(user)
                .amount(0)
                .createdAt(now)
                .modifiedAt(now)
                .build();
    }

    public UserPoint add(long amount, ClockHolder clockHolder) {
        if (amount == 0) {
            return this;
        }

        return UserPoint.builder()
                .user(user)
                .amount(this.amount + amount)
                .createdAt(createdAt)
                .modifiedAt(clockHolder.millis())
                .build();
    }
}
