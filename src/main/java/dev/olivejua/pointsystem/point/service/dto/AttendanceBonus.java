package dev.olivejua.pointsystem.point.service.dto;

import dev.olivejua.pointsystem.point.domain.PointAccrualType;
import dev.olivejua.pointsystem.point.service.port.PointTransactionRepository;
import dev.olivejua.pointsystem.user.domain.User;
import lombok.Getter;

@Getter
public class AttendanceBonus extends AbstractAccrualBonus {
    private final long amount = 10;

    public AttendanceBonus(User user) {
        super(user, PointAccrualType.ATTENDANCE_BONUS);
    }

    @Override
    public boolean isEligibleUserForPoints(PointTransactionRepository pointTransactionRepository) {
//        오늘치 중복해서 받으면 안됨
//        if (pointTransactionRepository.existsByAccrualTypeAndUserIdAndLocalDate()) {
//            return false;
//        }

        return true;
    }
}
