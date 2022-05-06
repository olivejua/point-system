package dev.olivejua.pointsystem.service;

import dev.olivejua.pointsystem.domain.User;
import dev.olivejua.pointsystem.web.dto.PurchaseRequest;

public interface PaymentService {
    Long purchase(PurchaseRequest request, User buyer);
}
