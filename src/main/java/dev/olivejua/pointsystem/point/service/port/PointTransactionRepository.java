package dev.olivejua.pointsystem.point.service.port;

import dev.olivejua.pointsystem.point.domain.PointTransaction;

public interface PointTransactionRepository {

    PointTransaction save(PointTransaction pointTransaction);

}
