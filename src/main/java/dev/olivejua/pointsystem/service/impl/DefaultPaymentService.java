package dev.olivejua.pointsystem.service.impl;

import dev.olivejua.pointsystem.domain.PaymentEntity;
import dev.olivejua.pointsystem.domain.Products;
import dev.olivejua.pointsystem.domain.UserEntity;
import dev.olivejua.pointsystem.repository.PaymentRepository;
import dev.olivejua.pointsystem.service.PaymentService;
import dev.olivejua.pointsystem.service.ProductService;
import dev.olivejua.pointsystem.web.dto.PurchaseRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class DefaultPaymentService implements PaymentService {
    private final PaymentRepository paymentRepository;

    private final ProductService productService;

    @Override
    public Long purchase(PurchaseRequest request, UserEntity buyer) {
        Products products = productService.findAll(request.getProductIds());

        PaymentEntity paymentEntity = PaymentEntity.create(buyer, products);
        paymentRepository.save(paymentEntity);

        return paymentEntity.getId();
    }



}
