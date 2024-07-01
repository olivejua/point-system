package dev.olivejua.pointsystem.order.domain;

import dev.olivejua.pointsystem.product.domain.Product;
import dev.olivejua.pointsystem.user.domain.User;
import lombok.Builder;
import lombok.Getter;

@Builder
@Getter
public class OrderCreate {
    private final Product product;
    private final User buyer;
    private final long points;

    public boolean hasPointsRedeemed() {
        return points > 0;
    }
}
