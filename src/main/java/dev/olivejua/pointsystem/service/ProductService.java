package dev.olivejua.pointsystem.service;

import dev.olivejua.pointsystem.web.dto.PurchaseRequest;

public interface ProductService {
    Long purchase(PurchaseRequest request);
}
