package dev.olivejua.pointsystem.order.service;

import dev.olivejua.pointsystem.common.exception.BuyerDoesNotMatchException;
import dev.olivejua.pointsystem.common.exception.NotFoundResourceException;
import dev.olivejua.pointsystem.common.service.DateTimeHolder;
import dev.olivejua.pointsystem.order.domain.Order;
import dev.olivejua.pointsystem.order.domain.OrderCreate;
import dev.olivejua.pointsystem.order.service.port.OrderRepository;
import lombok.Builder;

@Builder
public class OrderService {
    private final OrderRepository orderRepository;
    private final DateTimeHolder dateTimeHolder;

    public Order getById(long id) {
        return orderRepository.findById(id)
                .orElseThrow(() -> new NotFoundResourceException("Order"));
    }

    public Order order(OrderCreate orderCreate) {
        Order order = Order.from(orderCreate, dateTimeHolder);

        if (orderCreate.hasPointsRedeemed()) {
//            Points myPoints = pointService.redeem(buyer.getId(), orderCreate.getPoints());
//            order = order.deductAmount(myPoints);
        }

        order = orderRepository.save(order);

        return order;
    }

    public Order cancel(long id, long requestUserId) {
        Order order = orderRepository.findById(id)
                .orElseThrow(() -> new NotFoundResourceException("Order"));

        if (!order.hasSameBuyerAs(requestUserId)) {
            throw new BuyerDoesNotMatchException();
        }

        // TODO 사용포인트가 있다면 재적립 (사용주문건)
//        pointService.reaccrue(order);

        order = order.cancel(dateTimeHolder);

        return orderRepository.save(order);
    }

}
