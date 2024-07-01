package dev.olivejua.pointsystem.point.service.dto;

import dev.olivejua.pointsystem.point.domain.PointAccrualType;
import dev.olivejua.pointsystem.point.domain.PointTransactionType;
import dev.olivejua.pointsystem.point.service.port.PointTransactionRepository;
import dev.olivejua.pointsystem.user.domain.User;
import lombok.Getter;

@Getter
public abstract class AbstractAccrualBonus {
    protected final User user;
    protected final PointAccrualType accrualType;

    public AbstractAccrualBonus(User user, PointAccrualType accrualType) {
        this.user = user;
        this.accrualType = accrualType;
    }

    public abstract boolean isEligibleUserForPoints(PointTransactionRepository pointTransactionRepository);

    public abstract long getAmount();

    public long getUserId() {
        return user.getId();
    }

    public PointTransactionType getTransactionType() {
        return PointTransactionType.ACCRUAL;
    }
}
