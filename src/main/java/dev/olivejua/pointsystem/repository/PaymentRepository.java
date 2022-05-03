package dev.olivejua.pointsystem.repository;

import dev.olivejua.pointsystem.domain.Payment;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PaymentRepository extends JpaRepository<Payment, Long> {
}
