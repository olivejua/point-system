package dev.olivejua.pointsystem.order.service;

import dev.olivejua.pointsystem.order.controller.request.OrderCreateRequest;
import dev.olivejua.pointsystem.order.domain.OrderCreate;
import dev.olivejua.pointsystem.product.domain.Product;
import dev.olivejua.pointsystem.product.service.ProductService;
import dev.olivejua.pointsystem.user.domain.User;
import dev.olivejua.pointsystem.user.service.UserService;
import lombok.Builder;

import java.util.Objects;

@Builder
public class OrderCreateService {
    private final ProductService productService;
    private final UserService userService;

    public OrderCreate create(User customer, OrderCreateRequest request) {
        Objects.requireNonNull(customer);
        Objects.requireNonNull(request);

        Product product = productService.getById(request.productId());
        customer = userService.getById(customer.getId());

        return OrderCreate.builder()
                .product(product)
                .customer(customer)
                .points(request.points())
                .build();
    }
}
