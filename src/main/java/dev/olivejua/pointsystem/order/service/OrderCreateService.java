package dev.olivejua.pointsystem.order.service;

import dev.olivejua.pointsystem.common.exception.InvalidAttributeFormatException;
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

    /**
     * API의 요청정보를 유효성 검증 후 Domain으로 전환한다.
     * @param customer 주문 요청자
     * @param request 주문 API 요청 정보
     * @return 주문등록 데이터
     */
    public OrderCreate create(User customer, OrderCreateRequest request) {
        Objects.requireNonNull(customer);
        Objects.requireNonNull(request);

        if (request.hasPointsNegative()) {
            throw new InvalidAttributeFormatException("포인트");
        }

        Product product = productService.getById(request.productId());
        customer = userService.getById(customer.getId());

        return OrderCreate.builder()
                .product(product)
                .customer(customer)
                .points(request.points())
                .build();
    }
}
