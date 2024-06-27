package dev.olivejua.pointsystem.point.domain;

import dev.olivejua.pointsystem.order.domain.Order;
import dev.olivejua.pointsystem.user.domain.User;

import java.time.LocalDateTime;

public class PointTransaction {
    private long id;
    private User user;
    private PointTransactionType type;
    private PointAccrualType accrualType;
    private Order order;
    private long amount;
    private LocalDateTime createdAt;
}
