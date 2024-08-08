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
     * @param requestUser 주문 요청자
     * @param request 주문 API 요청 정보
     * @return 주문등록 데이터
     * @throws InvalidAttributeFormatException 포인트 금액이 음수일 경우 발생한다.
     */
    public OrderCreate create(User requestUser, OrderCreateRequest request) {
        validateRequest(requestUser, request);

        Product product = productService.getById(request.productId());
        User customer = userService.getById(requestUser.getId());

        return OrderCreate.builder()
                .product(product)
                .customer(customer)
                .points(request.points())
                .build();
    }

    private void validateRequest(User requestUser, OrderCreateRequest request) {
        Objects.requireNonNull(requestUser, "RequestUser cannot be null");
        Objects.requireNonNull(requestUser.getId(), "RequestUser cannot be null");
        Objects.requireNonNull(request, "OrderCreateRequest cannot be null");
        Objects.requireNonNull(request.productId(), "Product ID cannot be null");

        if (request.hasPointsNegative()) {
            throw new InvalidAttributeFormatException("포인트");
        }
    }
}
