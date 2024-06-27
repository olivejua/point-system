package dev.olivejua.pointsystem.repository;

import dev.olivejua.pointsystem.domain.PaymentEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PaymentRepository extends JpaRepository<PaymentEntity, Long> {
}
