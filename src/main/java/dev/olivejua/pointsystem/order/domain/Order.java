package dev.olivejua.pointsystem.order.domain;

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

    public static Order of(Product product, User buyer, DateTimeHolder dateTimeHolder) {
        return Order.builder()
                .buyer(buyer)
                .product(product)
                .amount(product.getPrice())
                .createdAt(dateTimeHolder.now())
                .build();
    }
}
