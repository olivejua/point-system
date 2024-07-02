package dev.olivejua.pointsystem.point.service;

import dev.olivejua.pointsystem.common.service.ClockHolder;
import dev.olivejua.pointsystem.point.domain.PointTransaction;
import dev.olivejua.pointsystem.point.domain.UserPoint;
import dev.olivejua.pointsystem.point.service.dto.AbstractAccrualBonus;
import dev.olivejua.pointsystem.point.service.port.PointTransactionRepository;
import dev.olivejua.pointsystem.point.service.port.UserPointRepository;
import lombok.Builder;

import java.util.Optional;

@Builder
public class PointService {
    private final PointTransactionRepository pointTransactionRepository;
    private final UserPointRepository userPointRepository;
    private final ClockHolder clockHolder;

    public Optional<PointTransaction> accrue(AbstractAccrualBonus accrualBonus) {
        if (!accrualBonus.isEligibleUserForPoints(pointTransactionRepository)) {
            return Optional.empty();
        }

        PointTransaction pointTransaction = PointTransaction.from(accrualBonus, clockHolder);
        pointTransaction = pointTransactionRepository.save(pointTransaction);

        UserPoint userPoint = userPointRepository.findByUserId(accrualBonus.getUserId())
                .orElse(UserPoint.initialFrom(accrualBonus.getUser(), clockHolder));

        userPoint = userPoint.add(pointTransaction.getAmount(), clockHolder);
        userPointRepository.save(userPoint);

        return Optional.of(pointTransaction);
    }
}
