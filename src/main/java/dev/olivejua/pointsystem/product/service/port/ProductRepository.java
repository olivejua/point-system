package dev.olivejua.pointsystem.product.service.port;

import dev.olivejua.pointsystem.product.domain.Product;

import java.util.Optional;

public interface ProductRepository {

    Optional<Product> findById(long id);

    Product save(Product product);

}
