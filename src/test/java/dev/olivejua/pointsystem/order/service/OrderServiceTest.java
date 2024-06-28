package dev.olivejua.pointsystem.order.service;

import dev.olivejua.pointsystem.common.exception.NotFoundResourceException;
import dev.olivejua.pointsystem.mock.FakeOrderRepository;
import dev.olivejua.pointsystem.mock.FakeProductRepository;
import dev.olivejua.pointsystem.mock.FakeUserRepository;
import dev.olivejua.pointsystem.mock.TestDateTimeHolder;
import dev.olivejua.pointsystem.order.domain.Order;
import dev.olivejua.pointsystem.order.domain.OrderCreate;
import dev.olivejua.pointsystem.order.domain.OrderStatus;
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
import static org.assertj.core.api.Assertions.assertThatThrownBy;

public class OrderServiceTest {
    private OrderService orderService;

    private FakeOrderRepository orderRepository = new FakeOrderRepository();

    private LocalDateTime now = LocalDateTime.now();

    @BeforeEach
    void setUp() {
        FakeUserRepository userRepository = new FakeUserRepository();
        FakeProductRepository productRepository = new FakeProductRepository();

        //ProductService, UserService의 의존성이 많아지면 인터페이스 분리하기
        orderService = OrderService.builder()
                .orderRepository(orderRepository)
                .productService(ProductService.builder()
                        .productRepository(productRepository)
                        .build())
                .userService(UserService.builder()
                        .userRepository(userRepository)
                        .build())
                .dateTimeHolder(new TestDateTimeHolder(now))
                .build();

        User buyer = userRepository.save(User.builder()
                .id(1L)
                .email("tmfrl4710@gmail.com")
                .nickname("olivejua")
                .status(UserStatus.ACTIVE)
                .createdAt(LocalDate.of(2024, 6, 1).atStartOfDay())
                .modifiedAt(LocalDate.of(2024, 6, 1).atStartOfDay())
                .build());
        Product product = productRepository.save(Product.builder()
                .id(1L)
                .name("자바의 신")
                .price(20_000)
                .createdAt(LocalDate.of(2024, 6, 1).atStartOfDay())
                .build());
        orderRepository.save(Order.builder()
                .buyer(buyer)
                .product(product)
                .amount(product.getPrice())
                .status(OrderStatus.ORDERED)
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
        assertThat(order.getId()).isEqualTo(2);
        assertThat(order.getProduct().getId()).isEqualTo(1);
        assertThat(order.getBuyer().getId()).isEqualTo(1);
        assertThat(order.getStatus()).isEqualTo(OrderStatus.ORDERED);
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

    @Test
    void 주문취소요청시_주문건이_존재하지_않으면_예외를_던진다() {
        //given
        //when
        //then
        assertThatThrownBy(() -> orderService.cancel(2, 1))
                .isInstanceOf(NotFoundResourceException.class);
    }

    @Test
    void 주문취소요청시_주문구매자와_주문취소자의_유저아이디가_다르다면_예외를_던진다() {
        //given
        //when
        //then
        assertThatThrownBy(() -> orderService.cancel(1, 2))
                .isInstanceOf(BuyerDoesNotMatchException.class);
    }
}