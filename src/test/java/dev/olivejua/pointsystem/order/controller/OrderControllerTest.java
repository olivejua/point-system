package dev.olivejua.pointsystem.order.controller;

import dev.olivejua.pointsystem.common.util.ClockUtil;
import dev.olivejua.pointsystem.mock.TestContainer;
import dev.olivejua.pointsystem.order.controller.request.OrderCreateRequest;
import dev.olivejua.pointsystem.order.domain.Order;
import dev.olivejua.pointsystem.point.domain.PointAccrualType;
import dev.olivejua.pointsystem.point.domain.UserPoint;
import dev.olivejua.pointsystem.product.domain.Product;
import dev.olivejua.pointsystem.user.domain.User;
import dev.olivejua.pointsystem.user.domain.UserStatus;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;

import java.time.Clock;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

class OrderControllerTest {
    private LocalDateTime now = LocalDateTime.now(Clock.systemUTC());

    @Test
    void 사용자는_상품을_주문할_수_있다() {
        //given
        TestContainer testContainer = TestContainer.builder()
                .clockHolder(() -> ClockUtil.toMillis(now))
                .dateTimeHolder(() -> now)
                .build();

        LocalDateTime createdAt = LocalDate.of(2024, 6, 1).atStartOfDay();
        Product product = testContainer.productRepository.save(Product.builder()
                .id(1L)
                .name("자바의 신")
                .price(20_000)
                .createdAt(createdAt)
                .build());

        long userCreatedAt = ClockUtil.toMillis(createdAt);
        User customer = testContainer.userRepository.save(User.builder()
                .id(1L)
                .email("tmfrl4710@gmail.com")
                .nickname("olivejua")
                .status(UserStatus.ACTIVE)
                .createdAt(userCreatedAt)
                .modifiedAt(userCreatedAt)
                .build());

        OrderCreateRequest orderCreateRequest = new OrderCreateRequest(product.getId(), customer.getId(), 0);

        //when
        ResponseEntity<Order> result = testContainer.orderController.order(orderCreateRequest);

        //then
        assertThat(result.getStatusCode()).isEqualTo(HttpStatusCode.valueOf(201));
        assertThat(result.getBody()).isNotNull();
        assertThat(result.getBody().getId()).isNotNull();
        assertThat(result.getBody().getProduct().getId()).isEqualTo(product.getId());
        assertThat(result.getBody().getCustomer().getId()).isEqualTo(customer.getId());
        assertThat(result.getBody().getCreatedAt()).isEqualTo(now);
    }

    @Test
    void 상품을_주문하면_주문포인트가_적립된다() {
        //given
        TestContainer testContainer = TestContainer.builder()
                .clockHolder(() -> ClockUtil.toMillis(now))
                .dateTimeHolder(() -> now)
                .build();

        LocalDateTime createdAt = LocalDate.of(2024, 6, 1).atStartOfDay();
        Product product = testContainer.productRepository.save(Product.builder()
                .id(1L)
                .name("자바의 신")
                .price(20_000)
                .createdAt(createdAt)
                .build());

        long userCreatedAt = ClockUtil.toMillis(createdAt);
        User customer = testContainer.userRepository.save(User.builder()
                .id(1L)
                .email("tmfrl4710@gmail.com")
                .nickname("olivejua")
                .status(UserStatus.ACTIVE)
                .createdAt(userCreatedAt)
                .modifiedAt(userCreatedAt)
                .build());

        OrderCreateRequest orderCreateRequest = new OrderCreateRequest(product.getId(), customer.getId(), 0);

        //when
        ResponseEntity<Order> result = testContainer.orderController.order(orderCreateRequest);

        //then
        assertThat(testContainer.pointTransactionRepository.existsByUserIdAndAccrualType(customer.getId(), PointAccrualType.ORDER_BONUS)).isTrue();

        Optional<UserPoint> userPoint = testContainer.userPointRepository.findByUserId(customer.getId());
        assertThat(userPoint.isPresent()).isTrue();
        assertThat(userPoint.get().getAmount()).isEqualTo(1_000);
    }
}