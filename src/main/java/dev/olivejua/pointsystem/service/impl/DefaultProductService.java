package dev.olivejua.pointsystem.service.impl;

import dev.olivejua.pointsystem.service.ProductService;
import dev.olivejua.pointsystem.web.dto.PurchaseRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class DefaultProductService implements ProductService {

    @Override
    public Long purchase(PurchaseRequest request) {

        return null;
    }
}
