package dev.olivejua.pointsystem.order.service;

import dev.olivejua.pointsystem.order.controller.request.OrderCreateRequest;
import dev.olivejua.pointsystem.order.domain.OrderCreate;
import dev.olivejua.pointsystem.product.domain.Product;
import dev.olivejua.pointsystem.product.service.ProductService;
import dev.olivejua.pointsystem.user.domain.User;
import dev.olivejua.pointsystem.user.service.UserService;
import lombok.Builder;

@Builder
public class OrderCreateService {
    private final ProductService productService;
    private final UserService userService;

    public OrderCreate create(OrderCreateRequest request) {
        Product product = productService.getById(request.productId());
        User buyer = userService.getById(request.buyerId());

        return OrderCreate.builder()
                .product(product)
                .buyer(buyer)
                .points(request.points())
                .build();
    }
}
