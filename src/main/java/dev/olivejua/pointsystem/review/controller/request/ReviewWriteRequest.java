package dev.olivejua.pointsystem.review.controller.request;

public record ReviewWriteRequest(
        long orderId,
        long writerId,
        String title,
        String content
) {}
