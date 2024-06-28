package dev.olivejua.pointsystem.mock;

import dev.olivejua.pointsystem.order.domain.Order;
import dev.olivejua.pointsystem.order.service.port.OrderRepository;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicLong;

public class FakeOrderRepository implements OrderRepository {
    private final AtomicLong autoGeneratedId = new AtomicLong(0);
    private final List<Order> data = new ArrayList<>();

    @Override
    public Order save(Order order) {
        if (order.getId() == null || order.getId() == 0) {
            Order newOrder = Order.builder()
                    .id(autoGeneratedId.incrementAndGet())
                    .buyer(order.getBuyer())
                    .product(order.getProduct())
                    .amount(order.getAmount())
                    .status(order.getStatus())
                    .createdAt(order.getCreatedAt())
                    .build();

            data.add(newOrder);
            return newOrder;
        } else {
            data.removeIf(item -> order.getId().equals(item.getId()));
            data.add(order);
            return order;
        }
    }
}
