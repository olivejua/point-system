package dev.olivejua.pointsystem.product.service;

import dev.olivejua.pointsystem.common.exception.NotFoundResourceException;
import dev.olivejua.pointsystem.product.domain.Product;
import dev.olivejua.pointsystem.product.service.port.ProductRepository;
import lombok.Builder;

@Builder
public class ProductService {
    private final ProductRepository productRepository;

    public Product getById(long id) {
        return productRepository.findById(id)
                .orElseThrow(() -> new NotFoundResourceException("Product"));
    }
}
