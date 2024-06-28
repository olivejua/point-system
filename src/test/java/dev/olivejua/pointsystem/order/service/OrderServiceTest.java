package dev.olivejua.pointsystem.order.service;

import dev.olivejua.pointsystem.mock.FakeOrderRepository;
import dev.olivejua.pointsystem.mock.FakeProductRepository;
import dev.olivejua.pointsystem.mock.FakeUserRepository;
import dev.olivejua.pointsystem.mock.TestDateTimeHolder;
import dev.olivejua.pointsystem.order.domain.Order;
import dev.olivejua.pointsystem.order.domain.OrderCreate;
import dev.olivejua.pointsystem.product.domain.Product;
import dev.olivejua.pointsystem.product.service.ProductService;
import dev.olivejua.pointsystem.user.domain.User;
import dev.olivejua.pointsystem.user.domain.UserStatus;
import dev.olivejua.pointsystem.user.service.UserService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.time.LocalDateTime;

import static org.assertj.core.api.Assertions.assertThat;

public class OrderServiceTest {
    private OrderService orderService;

    private LocalDateTime now = LocalDateTime.now();

    @BeforeEach
    void setUp() {
        FakeUserRepository userRepository = new FakeUserRepository();
        FakeProductRepository productRepository = new FakeProductRepository();

        //ProductService, UserService의 의존성이 많아지면 인터페이스 분리하기
        orderService = OrderService.builder()
                .orderRepository(new FakeOrderRepository())
                .productService(ProductService.builder()
                        .productRepository(productRepository)
                        .build())
                .userService(UserService.builder()
                        .userRepository(userRepository)
                        .build())
                .dateTimeHolder(new TestDateTimeHolder(now))
                .build();

        userRepository.save(User.builder()
                .id(1L)
                .email("tmfrl4710@gmail.com")
                .nickname("olivejua")
                .status(UserStatus.ACTIVE)
                .createdAt(LocalDate.of(2024, 6, 1).atStartOfDay())
                .modifiedAt(LocalDate.of(2024, 6, 1).atStartOfDay())
                .build());
        productRepository.save(Product.builder()
                .id(1L)
                .name("자바의 신")
                .price(20_000)
                .createdAt(LocalDate.of(2024, 6, 1).atStartOfDay())
                .build());
    }

    @Test
    void orderCreate로_주문객체를_저장할_수_있다() {
        //given
        OrderCreate orderCreate = new OrderCreate(1, 1, 0);

        //when
        Order order = orderService.order(orderCreate);

        //then
        assertThat(order.getId()).isEqualTo(1);
        assertThat(order.getProduct().getId()).isEqualTo(1);
        assertThat(order.getBuyer().getId()).isEqualTo(1);
        assertThat(order.getCreatedAt()).isEqualTo(now);
    }

    @Test
    void 포인트를_주문금액에서_차감할_수_있다() {
        //given

        //when

        //then
    }

    @Test
    void 주문금액의_5퍼센트를_포인트적립_한다() {
        //given

        //when

        //then
    }
}