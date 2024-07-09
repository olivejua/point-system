package dev.olivejua.pointsystem.review.service;

import dev.olivejua.pointsystem.common.util.ClockUtil;
import dev.olivejua.pointsystem.mock.FakeProductRepository;
import dev.olivejua.pointsystem.mock.FakeUserRepository;
import dev.olivejua.pointsystem.order.controller.request.OrderCreateRequest;
import dev.olivejua.pointsystem.order.domain.OrderCreate;
import dev.olivejua.pointsystem.product.domain.Product;
import dev.olivejua.pointsystem.product.service.ProductService;
import dev.olivejua.pointsystem.user.domain.User;
import dev.olivejua.pointsystem.user.domain.UserStatus;
import dev.olivejua.pointsystem.user.service.UserService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;

import static org.assertj.core.api.Assertions.assertThat;

class OrderCreateServiceTest {
    private OrderCreateService orderCreateService;
    private FakeProductRepository fakeProductRepository;
    private FakeUserRepository fakeUserRepository;

    @BeforeEach
    void setUp() {
        fakeProductRepository = new FakeProductRepository();
        fakeUserRepository = new FakeUserRepository();
        orderCreateService = OrderCreateService.builder()
                .productService(ProductService.builder()
                        .productRepository(fakeProductRepository)
                        .build())
                .userService(UserService.builder()
                        .userRepository(fakeUserRepository)
                        .build())
                .build();
    }

    @Test
    void OrderCreateRequest로_필요데이터를_조회하여_OrderCreate객체를_생성한다() {
        //given
        Product product = fakeProductRepository.save(Product.builder()
                .id(1L)
                .name("자바의 신")
                .price(20_000)
                .createdAt(LocalDate.of(2024, 6, 1).atStartOfDay())
                .build());
        User user = fakeUserRepository.save(User.builder()
                .id(1L)
                .email("tmfrl4710@gmail.com")
                .nickname("olivejua")
                .status(UserStatus.ACTIVE)
                .createdAt(ClockUtil.toMillis(LocalDate.of(2024, 6, 1).atStartOfDay()))
                .modifiedAt(ClockUtil.toMillis(LocalDate.of(2024, 6, 1).atStartOfDay()))
                .build());

        OrderCreateRequest request = new OrderCreateRequest(product.getId(), user.getId(), 1000);

        //when
        OrderCreate orderCreate = orderCreateService.create(request);

        //then
        assertThat(orderCreate).isNotNull();
        assertThat(orderCreate.getProduct().getId()).isEqualTo(product.getId());
        assertThat(orderCreate.getProduct().getName()).isEqualTo(product.getName());
        assertThat(orderCreate.getProduct().getPrice()).isEqualTo(product.getPrice());
        assertThat(orderCreate.getBuyer().getId()).isEqualTo(user.getId());
        assertThat(orderCreate.getBuyer().getEmail()).isEqualTo(user.getEmail());
        assertThat(orderCreate.getBuyer().getNickname()).isEqualTo(user.getNickname());
        assertThat(orderCreate.getPoints()).isEqualTo(1000);
    }
}