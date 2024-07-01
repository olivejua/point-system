package dev.olivejua.pointsystem.point.domain;

import dev.olivejua.pointsystem.common.service.ClockHolder;
import dev.olivejua.pointsystem.order.domain.Order;
import dev.olivejua.pointsystem.point.service.dto.AbstractAccrualBonus;
import dev.olivejua.pointsystem.user.domain.User;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class PointTransaction {
    private final Long id;
    private final User user;
    private final PointTransactionType type;
    private final PointAccrualType accrualType;
    private final Order order;
    private final long amount;
    private final long createdAt;

    public static PointTransaction from(AbstractAccrualBonus accrualBonus, ClockHolder clockHolder) {
        return PointTransaction.builder()
                .user(accrualBonus.getUser())
                .type(accrualBonus.getTransactionType())
                .accrualType(accrualBonus.getAccrualType())
                .amount(accrualBonus.getAmount())
                .createdAt(clockHolder.millis())
                .build();
    }
}
