package dev.olivejua.pointsystem.point.infrastructure;

import dev.olivejua.pointsystem.point.domain.UserPoint;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserPointRepository extends JpaRepository<UserPoint, Long> {
}
