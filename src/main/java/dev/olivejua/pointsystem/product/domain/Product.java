package dev.olivejua.pointsystem.product.domain;

import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;

@Builder
@Getter
public class Product {
    private final Long id;
    private final String name;
    private final long price;
    private final LocalDateTime createdAt;
}
