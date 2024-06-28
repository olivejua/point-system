package dev.olivejua.pointsystem.order.service.port;

import dev.olivejua.pointsystem.order.domain.Order;

import java.util.Optional;

public interface OrderRepository {

    Optional<Order> findById(long id);

    Order save(Order order);

}
