package dev.olivejua.pointsystem.service.impl;

import dev.olivejua.pointsystem.domain.ProductEntity;
import dev.olivejua.pointsystem.domain.Products;
import dev.olivejua.pointsystem.repository.ProductRepository;
import dev.olivejua.pointsystem.service.ProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@RequiredArgsConstructor
@Service
public class DefaultProductService implements ProductService {
    private final ProductRepository productRepository;

    public Products findAll(List<Long> productIds) {
        List<ProductEntity> findProductEntities = productRepository.findAllById(productIds);

        if (findProductEntities.size() < productIds.size()) {
            throw new RuntimeException("없는 상품은 뭐야!!");
        }

        return Products.create(findProductEntities);
    }
}
