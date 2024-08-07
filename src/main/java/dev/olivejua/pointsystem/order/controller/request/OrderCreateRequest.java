package dev.olivejua.pointsystem.order.controller.request;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.PositiveOrZero;

public record OrderCreateRequest(
        @NotNull @PositiveOrZero
        long productId,
        @NotNull @PositiveOrZero
        long customerId,
        @PositiveOrZero
        long points
) {}
