package dev.olivejua.pointsystem.point.service.dto;

import dev.olivejua.pointsystem.point.domain.PointAccrualType;
import dev.olivejua.pointsystem.point.service.port.PointTransactionRepository;
import dev.olivejua.pointsystem.user.domain.User;
import lombok.Getter;

@Getter
public class JoinBonus extends AbstractAccrualBonus {
    private final long amount = 1_000;

    public JoinBonus(User user) {
        super(user, PointAccrualType.JOIN_BONUS);
    }

    @Override
    public boolean isEligibleUserForPoints(PointTransactionRepository pointTransactionRepository) {
//        boolean isCreatedDateToday = user.getCreatedAt().toLocalDate()
//                .equals(LocalDateTime.now().toLocalDate());
//
//        if (!isCreatedDateToday) {
//            return false;
//        }

//        if (pointTransactionRepository.existsByUserIdAndAccrualTypeCode()) {
//            return false;
//        }

        return true;
    }
}
