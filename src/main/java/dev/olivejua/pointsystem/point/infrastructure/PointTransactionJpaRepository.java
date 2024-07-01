package dev.olivejua.pointsystem.point.infrastructure;

import org.springframework.data.jpa.repository.JpaRepository;

public interface PointTransactionJpaRepository extends JpaRepository<PointTransactionEntity, Long> {
}
