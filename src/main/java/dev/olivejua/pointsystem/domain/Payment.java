package dev.olivejua.pointsystem.domain;

import lombok.Getter;
import org.hibernate.annotations.Comment;

import javax.persistence.*;

import static javax.persistence.FetchType.LAZY;
import static javax.persistence.GenerationType.IDENTITY;

@Getter
@Entity
public class Payment extends BaseTimeEntity {

    @Column(name = "PAYMENT_ID")
    @Id @GeneratedValue(strategy = IDENTITY)
    private Long id;

    @Comment("구매자")
    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "USER_ID")
    private User buyer;

    @Comment("결제금액")
    private int amount;

    public static Payment create(User buyer, Products purchasedItems) {
        Payment payment = new Payment();
        payment.buyer = buyer;
        payment.amount = purchasedItems.totalPrice();
        return payment;
    }
}
