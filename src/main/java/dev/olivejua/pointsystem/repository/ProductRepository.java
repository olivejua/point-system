package dev.olivejua.pointsystem.repository;

import dev.olivejua.pointsystem.domain.ProductEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductRepository extends JpaRepository<ProductEntity, Long> {
}
