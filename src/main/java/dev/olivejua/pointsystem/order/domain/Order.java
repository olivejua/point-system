package dev.olivejua.pointsystem.order.domain;

import dev.olivejua.pointsystem.common.exception.IllegalStatusException;
import dev.olivejua.pointsystem.common.service.DateTimeHolder;
import dev.olivejua.pointsystem.product.domain.Product;
import dev.olivejua.pointsystem.user.domain.User;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
@Builder
public class Order {
    private Long id;
    private User buyer;
    private Product product;
    private long amount;
    private OrderStatus status;
    private LocalDateTime createdAt;
    private LocalDateTime modifiedAt;

    public static Order from(OrderCreate orderCreate, DateTimeHolder dateTimeHolder) {
        LocalDateTime now = dateTimeHolder.now();
        return Order.builder()
                .buyer(orderCreate.getBuyer())
                .product(orderCreate.getProduct())
                .amount(orderCreate.getProduct().getPrice())
                .status(OrderStatus.ORDERED)
                .createdAt(now)
                .modifiedAt(now)
                .build();
    }

    public boolean hasSameBuyerAs(long userId) {
        return buyer.getId() == userId;
    }

    public boolean isCanceled() {
        return status == OrderStatus.CANCELED;
    }

    public Order cancel(DateTimeHolder dateTimeHolder) {
        if (status == OrderStatus.CANCELED) {
            throw new IllegalStatusException("이미 주문취소 상태입니다.");
        }

        return Order.builder()
                .buyer(buyer)
                .product(product)
                .amount(product.getPrice())
                .status(OrderStatus.CANCELED)
                .createdAt(createdAt)
                .modifiedAt(dateTimeHolder.now())
                .build();
    }
}
