package dev.olivejua.pointsystem.point.infrastructure;

import dev.olivejua.pointsystem.point.domain.PointTransaction;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PointTransactionRepository extends JpaRepository<PointTransaction, Long> {
}
