package dev.olivejua.pointsystem.repository;

import dev.olivejua.pointsystem.domain.Product;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductRepository extends JpaRepository<Product, Long> {
}
