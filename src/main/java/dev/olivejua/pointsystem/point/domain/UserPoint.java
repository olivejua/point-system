package dev.olivejua.pointsystem.point.domain;

import dev.olivejua.pointsystem.common.service.DateTimeHolder;
import dev.olivejua.pointsystem.user.domain.User;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
@Builder
public class UserPoint {
    private final Long id;
    private final User user;
    private final long amount;
    private final LocalDateTime createdAt;
    private final LocalDateTime modifiedAt;

    public static UserPoint initialFrom(User user, DateTimeHolder dateTimeHolder) {
        LocalDateTime now = dateTimeHolder.now();

        return UserPoint.builder()
                .user(user)
                .amount(0)
                .createdAt(now)
                .modifiedAt(now)
                .build();
    }

    public UserPoint add(long amount, DateTimeHolder dateTimeHolder) {
        return UserPoint.builder()
                .user(user)
                .amount(this.amount + amount)
                .createdAt(createdAt)
                .modifiedAt(dateTimeHolder.now())
                .build();
    }
}
