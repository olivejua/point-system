package dev.olivejua.pointsystem.point.domain.accrualbonus;

import dev.olivejua.pointsystem.common.service.ClockHolder;
import dev.olivejua.pointsystem.common.util.ClockUtil;
import dev.olivejua.pointsystem.point.domain.PointAccrualType;
import dev.olivejua.pointsystem.point.service.port.PointTransactionRepository;
import dev.olivejua.pointsystem.user.domain.User;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDate;

@Getter
public class AttendanceBonus extends AbstractAccrualBonus {
    private final long amount = 10;
    private final ClockHolder clockHolder;

    @Builder
    public AttendanceBonus(User user, ClockHolder clockHolder) {
        super(user, PointAccrualType.ATTENDANCE_BONUS);
        this.clockHolder = clockHolder;
    }

    @Override
    public boolean isEligibleUserForPoints(PointTransactionRepository pointTransactionRepository) {
        final LocalDate targetDay = ClockUtil.toLocalDate(clockHolder.millis());

        return !pointTransactionRepository.existsByUserIdAndAccrualTypeAndCreatedAtBetween(
                user.getId(),
                accrualType,
                ClockUtil.toMillis(targetDay.atStartOfDay()),
                ClockUtil.toMillis(targetDay.atTime(23, 59, 59)));
    }
}
