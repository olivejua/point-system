package dev.olivejua.pointsystem.order.service.port;

import dev.olivejua.pointsystem.order.domain.Order;

public interface OrderRepository {

    Order save(Order order);

}
