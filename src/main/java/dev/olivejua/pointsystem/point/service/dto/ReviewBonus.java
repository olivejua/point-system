package dev.olivejua.pointsystem.point.service.dto;

import dev.olivejua.pointsystem.point.domain.PointAccrualType;
import dev.olivejua.pointsystem.point.service.port.PointTransactionRepository;
import dev.olivejua.pointsystem.user.domain.User;

public class ReviewBonus extends AbstractAccrualBonus {
    private static final int DAILY_MAX_COUNT = 3;
    private final long amount = 500;

    public ReviewBonus(User user) {
        super(user, PointAccrualType.REVIEW_BONUS);
    }

    @Override
    public boolean isEligibleUserForPoints(PointTransactionRepository pointTransactionRepository) {
//        int count = pointTransactionRepository.countByLocalDateAndUserIdAndAccrualType();
//        if (DAILY_MAX_COUNT <= count) {
//            return false;
//        }

        return true;
    }

    @Override
    public long getAmount() {
        return amount;
    }
}
