package dev.olivejua.pointsystem.point.infrastructure;

import org.springframework.data.jpa.repository.JpaRepository;

public interface PointTransactionRepository extends JpaRepository<PointTransactionEntity, Long> {
}
