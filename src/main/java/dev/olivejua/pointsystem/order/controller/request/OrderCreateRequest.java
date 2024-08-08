package dev.olivejua.pointsystem.order.controller.request;

import java.util.Objects;

/**
 * 사용자로부터 받는 상품주문 요청 데이터
 * @param productId 상품 ID
 * @param points 사용 포인트 금액
 */
public record OrderCreateRequest(
        Long productId,
        long points
) {
        public OrderCreateRequest {
                Objects.requireNonNull(productId, "Product ID must not be null");

                if (points < 0) {
                        throw new IllegalArgumentException("Points cannot be negative");
                }
        }
}
