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

    public ResponseEntity<Order> order(OrderCreateRequest request) {
        final OrderCreate orderCreate = orderCreateService.create(request);
        final Order order = orderService.order(orderCreate);
        pointService.accrue(new OrderBonus(order));

        return ResponseEntity
                .created(URI.create(environmentProperties.getHost() + "/" + PREFIX_PATH + "/" + order.getId()))
                .body(order);
    }

    public ResponseEntity<Order> cancel(long orderId, User user) {
        Order order = orderService.cancel(orderId, user.getId());
        // TODO 적립포인트 회수 (사용주문건)
//        pointService.reverse(order);

        return ResponseEntity.ok(order);
    }
}
