package dev.olivejua.pointsystem.order.controller.request;

public record OrderCreateRequest(
        long productId,
        long points
) {
        public boolean hasPointsNegative() {
                return points < 0;
        }
}
