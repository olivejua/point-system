package dev.olivejua.pointsystem.order.service;

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
import static org.assertj.core.api.Assertions.assertThatThrownBy;

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

        OrderCreateRequest request = new OrderCreateRequest(product.getId(), 1000);

        //when
        OrderCreate orderCreate = orderCreateService.create(user, request);

        //then
        assertThat(orderCreate).isNotNull();
        assertThat(orderCreate.getProduct().getId()).isEqualTo(product.getId());
        assertThat(orderCreate.getProduct().getName()).isEqualTo(product.getName());
        assertThat(orderCreate.getProduct().getPrice()).isEqualTo(product.getPrice());
        assertThat(orderCreate.getCustomer().getId()).isEqualTo(user.getId());
        assertThat(orderCreate.getCustomer().getEmail()).isEqualTo(user.getEmail());
        assertThat(orderCreate.getCustomer().getNickname()).isEqualTo(user.getNickname());
        assertThat(orderCreate.getPoints()).isEqualTo(1000);
    }

    @Test
    void orderCreateRequest_파라미터를_null로_전달하면_예외가_발생한다() {
        //given
        User requestUser = fakeUserRepository.save(User.builder()
                .id(1L)
                .email("tmfrl4710@gmail.com")
                .nickname("olivejua")
                .status(UserStatus.ACTIVE)
                .createdAt(ClockUtil.toMillis(LocalDate.of(2024, 6, 1).atStartOfDay()))
                .modifiedAt(ClockUtil.toMillis(LocalDate.of(2024, 6, 1).atStartOfDay()))
                .build());

        //when
        //then
        assertThatThrownBy(() -> orderCreateService.create(requestUser, null))
                .isInstanceOf(NullPointerException.class)
                .hasMessage("OrderCreateRequest cannot be null");
    }

    @Test
    void requestUser_파라미터를_null로_전달하면_예외가_발생한다() {
        //given
        OrderCreateRequest orderCreateRequest = new OrderCreateRequest(1L, 0);

        //when
        //then
        assertThatThrownBy(() -> orderCreateService.create(null, orderCreateRequest))
                .isInstanceOf(NullPointerException.class)
                .hasMessage("RequestUser cannot be null");
    }
}