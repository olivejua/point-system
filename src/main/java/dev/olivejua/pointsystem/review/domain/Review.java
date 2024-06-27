package dev.olivejua.pointsystem.review.domain;

import dev.olivejua.pointsystem.order.domain.Order;
import dev.olivejua.pointsystem.user.domain.User;

import java.time.LocalDateTime;

public class Review {
    private Long id;
    private Order order;
    private User writer;
    private String title;
    private String content;
    private LocalDateTime createdAt;
    private LocalDateTime modifiedAt;
}
