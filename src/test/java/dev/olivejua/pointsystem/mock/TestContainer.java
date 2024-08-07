package dev.olivejua.pointsystem.mock;

import dev.olivejua.pointsystem.common.infrastructure.properties.EnvironmentProperties;
import dev.olivejua.pointsystem.common.service.ClockHolder;
import dev.olivejua.pointsystem.common.service.DateTimeHolder;
import dev.olivejua.pointsystem.order.controller.OrderController;
import dev.olivejua.pointsystem.order.service.OrderCreateService;
import dev.olivejua.pointsystem.order.service.OrderService;
import dev.olivejua.pointsystem.order.service.port.OrderRepository;
import dev.olivejua.pointsystem.point.service.PointService;
import dev.olivejua.pointsystem.point.service.port.PointTransactionRepository;
import dev.olivejua.pointsystem.point.service.port.UserPointRepository;
import dev.olivejua.pointsystem.product.service.ProductService;
import dev.olivejua.pointsystem.product.service.port.ProductRepository;
import dev.olivejua.pointsystem.user.controller.UserController;
import dev.olivejua.pointsystem.user.service.UserService;
import dev.olivejua.pointsystem.user.service.port.UserRepository;
import lombok.Builder;

public class TestContainer {
    public final EnvironmentProperties environmentProperties;

    public final UserRepository userRepository;
    public final PointTransactionRepository pointTransactionRepository;
    public final UserPointRepository userPointRepository;
    public final ProductRepository productRepository;
    public final OrderRepository orderRepository;

    public final UserService userService;
    public final PointService pointService;
    public final ProductService productService;
    public final OrderCreateService orderCreateService;
    public final OrderService orderService;

    public final UserController userController;
    public final OrderController orderController;


    @Builder
    public TestContainer(ClockHolder clockHolder, DateTimeHolder dateTimeHolder) {
        this.environmentProperties = new EnvironmentProperties();

        this.userRepository = new FakeUserRepository();
        this.pointTransactionRepository = new FakePointTransactionRepository();
        this.userPointRepository = new FakeUserPointRepository();
        this.productRepository = new FakeProductRepository();
        this.orderRepository = new FakeOrderRepository();

        this.userService = UserService.builder()
                .userRepository(userRepository)
                .clockHolder(clockHolder)
                .build();
        this.pointService = PointService.builder()
                .pointTransactionRepository(pointTransactionRepository)
                .userPointRepository(userPointRepository)
                .clockHolder(clockHolder)
                .build();
        this.productService = ProductService.builder()
                .productRepository(productRepository)
                .build();
        this.orderCreateService = OrderCreateService.builder()
                .productService(productService)
                .userService(userService)
                .build();
        this.orderService = OrderService.builder()
                .orderRepository(orderRepository)
                .dateTimeHolder(dateTimeHolder)
                .build();

        this.userController = UserController.builder()
                .userService(userService)
                .pointService(pointService)
                .clockHolder(clockHolder)
                .build();
        this.orderController = OrderController.builder()
                .orderCreateService(orderCreateService)
                .orderService(orderService)
                .pointService(pointService)
                .environmentProperties(environmentProperties)
                .build();
    }
}
