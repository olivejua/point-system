package dev.olivejua.pointsystem.point.infrastructure;

import dev.olivejua.pointsystem.point.domain.PointAccrualType;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PointAccrualTypeRepository extends JpaRepository<PointAccrualType, Long> {
}
