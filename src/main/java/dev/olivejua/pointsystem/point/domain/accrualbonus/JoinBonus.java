package dev.olivejua.pointsystem.point.domain.accrualbonus;

import dev.olivejua.pointsystem.common.util.ClockUtil;
import dev.olivejua.pointsystem.point.domain.PointAccrualType;
import dev.olivejua.pointsystem.point.service.port.PointTransactionRepository;
import dev.olivejua.pointsystem.user.domain.User;
import lombok.Getter;

import java.time.LocalDate;

@Getter
public class JoinBonus extends AbstractAccrualBonus {
    private final long amount = 1_000;

    public JoinBonus(User user) {
        super(user, PointAccrualType.JOIN_BONUS);
    }

    @Override
    public boolean isEligibleUserForPoints(PointTransactionRepository pointTransactionRepository) {
        boolean isCreatedDateToday = ClockUtil.toLocalDate(user.getCreatedAt())
                .equals(LocalDate.now());

        if (!isCreatedDateToday) {
            return false;
        }

        return !pointTransactionRepository.existsByUserIdAndAccrualType(user.getId(), accrualType);
    }
}
