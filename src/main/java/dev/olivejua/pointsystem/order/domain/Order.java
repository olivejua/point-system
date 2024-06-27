package dev.olivejua.pointsystem.order.domain;

import dev.olivejua.pointsystem.product.domain.Product;
import dev.olivejua.pointsystem.user.domain.User;

import java.time.LocalDateTime;

public class Order {
    private Long id;
    private User buyer;
    private Product product;
    private int amount;
    private OrderStatus status;
    private LocalDateTime createdAt;
}
