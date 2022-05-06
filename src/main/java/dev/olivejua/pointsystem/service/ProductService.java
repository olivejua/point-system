package dev.olivejua.pointsystem.service;

import dev.olivejua.pointsystem.domain.Products;

import java.util.List;

public interface ProductService {
    Products findAll(List<Long> productIds);
}
