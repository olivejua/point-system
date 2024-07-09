package dev.olivejua.pointsystem.order.controller.request;

public record OrderCreateRequest(
        long productId,
        long buyerId,
        long points
) {}
