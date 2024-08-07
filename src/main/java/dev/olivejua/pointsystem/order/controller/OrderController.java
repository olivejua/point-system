package dev.olivejua.pointsystem.order.controller;

import dev.olivejua.pointsystem.common.infrastructure.properties.EnvironmentProperties;
import dev.olivejua.pointsystem.order.controller.request.OrderCreateRequest;
import dev.olivejua.pointsystem.order.domain.Order;
import dev.olivejua.pointsystem.order.domain.OrderCreate;
import dev.olivejua.pointsystem.order.service.OrderCreateService;
import dev.olivejua.pointsystem.order.service.OrderService;
import dev.olivejua.pointsystem.point.domain.accrualbonus.OrderBonus;
import dev.olivejua.pointsystem.point.service.PointService;
import dev.olivejua.pointsystem.user.domain.User;
import lombok.Builder;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;

import java.net.URI;

@RequestMapping(OrderController.PREFIX_PATH)
@Builder
public class OrderController {
    private final OrderCreateService orderCreateService;
    private final OrderService orderService;
    private final PointService pointService;
    private final EnvironmentProperties environmentProperties;
    static final String PREFIX_PATH = "orders";

    /**
     * 사용자로부터 상품 주문을 받고, 주문건에 대한 포인트를 적립한다.
     *
     * @param request 주문 요청 정보
     * @param customer 주문 요청자
     * @return 등록된 주문 정보
     */
    public ResponseEntity<Order> order(User customer, OrderCreateRequest request) {
        final OrderCreate orderCreate = orderCreateService.create(customer, request);
        final Order order = orderService.order(orderCreate);
        pointService.accrue(new OrderBonus(order));

        return ResponseEntity
                .created(URI.create(environmentProperties.getHost() + "/" + PREFIX_PATH + "/" + order.getId()))
                .body(order);
    }

    /**
     * 사용자로부터 상품 취소 요청을 받고, 취소 주문건에 대한 포인트 내역도 취소한다.
     *
     * @param orderId 주문 ID
     * @param customer 취소 요청자
     * @return 취소 주문정보
     */
    public ResponseEntity<Order> cancel(User customer, long orderId) {
        Order order = orderService.cancel(customer.getId(), orderId);
        // TODO 적립포인트 회수 (사용주문건)
//        pointService.reverse(order);

        return ResponseEntity.ok(order);
    }
}
