package dev.olivejua.pointsystem.point.domain.accrualbonus;

import dev.olivejua.pointsystem.order.domain.Order;
import dev.olivejua.pointsystem.point.domain.PointAccrualType;
import dev.olivejua.pointsystem.point.service.port.PointTransactionRepository;

public class OrderBonus extends AbstractAccrualBonus {
    private static final double PERCENTAGE_RATE = 0.05;
    private final long amount;

    public OrderBonus(Order order) {
        super(order.getBuyer(), PointAccrualType.ORDER_BONUS);

        this.amount = Math.round(order.getAmount() * PERCENTAGE_RATE);
    }

    @Override
    public boolean isEligibleUserForPoints(PointTransactionRepository pointTransactionRepository) {
        return true;
    }

    @Override
    public long getAmount() {
        return amount;
    }
}
