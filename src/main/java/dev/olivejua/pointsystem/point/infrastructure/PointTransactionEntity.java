package dev.olivejua.pointsystem.point.infrastructure;

import dev.olivejua.pointsystem.order.infrastructure.OrderEntity;
import dev.olivejua.pointsystem.user.infrastructure.UserEntity;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.time.LocalDateTime;

@RequiredArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
@Table(name = "point_transactions")
@Entity
public class PointTransactionEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    private UserEntity user;

    @Column(nullable = false)
    private String type;

    private String accrualType;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "order_id")
    private OrderEntity order;

    @Column(nullable = false)
    private int amount;

    @Column(nullable = false)
    private LocalDateTime createdAt;
}
