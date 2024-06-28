package dev.olivejua.pointsystem.order.domain;

import lombok.Getter;

@Getter
public class OrderCreate {
    private final long productId;
    private final long buyerId;
    private final long points;

    public OrderCreate(long productId, long buyerId, long points) {
        this.productId = productId;
        this.buyerId = buyerId;
        this.points = points;
    }

    public boolean hasPointsRedeemed() {
        return points > 0;
    }
}
