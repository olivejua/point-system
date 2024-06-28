package dev.olivejua.pointsystem.order.service;

import dev.olivejua.pointsystem.common.service.DateTimeHolder;
import dev.olivejua.pointsystem.order.domain.Order;
import dev.olivejua.pointsystem.order.domain.OrderCreate;
import dev.olivejua.pointsystem.order.service.port.OrderRepository;
import dev.olivejua.pointsystem.product.domain.Product;
import dev.olivejua.pointsystem.product.service.ProductService;
import dev.olivejua.pointsystem.user.domain.User;
import dev.olivejua.pointsystem.user.service.UserService;
import lombok.Builder;

@Builder
public class OrderService {
    private final OrderRepository orderRepository;
    private final ProductService productService;
    private final UserService userService;
    private final DateTimeHolder dateTimeHolder;

    public Order order(OrderCreate orderCreate) {
        Product product = productService.getById(orderCreate.getProductId());
        User buyer = userService.getById(orderCreate.getBuyerId());

        Order order = Order.of(product, buyer, dateTimeHolder);

        if (orderCreate.hasPointsRedeemed()) {
//            Points myPoints = pointService.redeem(buyer.getId(), orderCreate.getPoints());
//            order = order.deductAmount(myPoints);
        }

        order = orderRepository.save(order);

        // TODO 포인트 적립

        return order;
    }


}
