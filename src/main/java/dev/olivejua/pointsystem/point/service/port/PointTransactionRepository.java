package dev.olivejua.pointsystem.point.service.port;

import dev.olivejua.pointsystem.point.domain.PointAccrualType;
import dev.olivejua.pointsystem.point.domain.PointTransaction;

public interface PointTransactionRepository {

    PointTransaction save(PointTransaction pointTransaction);

    boolean existsByUserIdAndAccrualType(Long userId, PointAccrualType accrualType);

    boolean existsByUserIdAndAccrualTypeAndCreatedAtBetween(Long userId, PointAccrualType accrualType, long startMillis, long endMillis);

    long countByLocalDateAndUserIdAndAccrualType(Long userId, PointAccrualType accrualType);

}
