package dev.olivejua.pointsystem.domain;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.hibernate.annotations.Comment;

import java.time.LocalDateTime;

import static jakarta.persistence.FetchType.LAZY;
import static jakarta.persistence.GenerationType.IDENTITY;

@RequiredArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
@Table(name = "payments")
@Entity
public class PaymentEntity {

    @Column(name = "PAYMENT_ID")
    @Id
    @GeneratedValue(strategy = IDENTITY)
    private Long id;

    @Comment("구매자")
    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "USER_ID")
    private UserEntity buyer;

    @Comment("결제금액")
    private int amount;

    @Column(nullable = false)
    private LocalDateTime createdAt;

    public static PaymentEntity create(UserEntity buyer, Products purchasedItems) {
        PaymentEntity paymentEntity = new PaymentEntity();
        paymentEntity.buyer = buyer;
        paymentEntity.amount = purchasedItems.totalPrice();
        return paymentEntity;
    }
}
