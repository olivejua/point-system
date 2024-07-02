package dev.olivejua.pointsystem.order.domain;

import dev.olivejua.pointsystem.common.exception.IllegalStatusException;
import dev.olivejua.pointsystem.common.util.ClockUtil;
import dev.olivejua.pointsystem.mock.TestDateTimeHolder;
import dev.olivejua.pointsystem.product.domain.Product;
import dev.olivejua.pointsystem.user.domain.User;
import dev.olivejua.pointsystem.user.domain.UserStatus;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.time.LocalDateTime;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

public class OrderTest {

    @Test
    void Product과_User로_객체를_생성할_수_있다() {
        //given
        long createdDate = ClockUtil.millisFrom(LocalDate.of(2024, 6, 1).atStartOfDay());

        Product product = Product.builder()
                .id(1L)
                .name("세이노의 가르침")
                .price(10_000)
                .createdAt(LocalDate.of(2024, 6, 1).atStartOfDay())
                .build();
        User buyer = User.builder()
                .id(1L)
                .email("tmfrl4710@gmail.com")
                .nickname("olivejua")
                .status(UserStatus.ACTIVE)
                .createdAt(createdDate)
                .modifiedAt(createdDate)
                .build();

        //when
        LocalDateTime now = LocalDateTime.now();
        OrderCreate orderCreate = OrderCreate.builder()
                .product(product)
                .buyer(buyer)
                .build();
        Order order = Order.from(orderCreate, new TestDateTimeHolder(now));

        //then
        assertThat(order.getBuyer()).isEqualTo(buyer);
        assertThat(order.getProduct()).isEqualTo(product);
        assertThat(order.getAmount()).isEqualTo(10_000);
        assertThat(order.getCreatedAt()).isEqualTo(now);
    }

    @Test
    void hasSameBuyerAs로_구매자와_취소_요청자와_다른_유저아이디라면_false를_반환한다() {
        //given
        long createdDate = ClockUtil.millisFrom(LocalDate.of(2024, 6, 1).atStartOfDay());
        Order order = Order.builder()
                .id(1L)
                .buyer(User.builder()
                        .id(1L)
                        .email("tmfrl4710@gmail.com")
                        .nickname("olivejua")
                        .status(UserStatus.ACTIVE)
                        .createdAt(createdDate)
                        .modifiedAt(createdDate)
                        .build())
                .product(Product.builder()
                        .id(1L)
                        .name("자바의 신")
                        .price(10_000)
                        .createdAt(LocalDate.of(2024, 6, 1).atStartOfDay())
                        .build())
                .amount(10_000)
                .createdAt(LocalDate.of(2024, 6, 1).atStartOfDay())
                .modifiedAt(LocalDate.of(2024, 6, 1).atStartOfDay())
                .status(OrderStatus.ORDERED)
                .build();

        //when
        boolean result = order.hasSameBuyerAs(2);

        //then
        assertThat(result).isFalse();
    }

    @Test
    void hasSameBuyerAs로_구매자와_취소_요청자와_동일한_유저아이디라면_true를_반환한다() {
        //given
        long createdDate = ClockUtil.millisFrom(LocalDate.of(2024, 6, 1).atStartOfDay());
        Order order = Order.builder()
                .id(1L)
                .buyer(User.builder()
                        .id(1L)
                        .email("tmfrl4710@gmail.com")
                        .nickname("olivejua")
                        .status(UserStatus.ACTIVE)
                        .createdAt(createdDate)
                        .modifiedAt(createdDate)
                        .build())
                .product(Product.builder()
                        .id(1L)
                        .name("자바의 신")
                        .price(10_000)
                        .createdAt(LocalDate.of(2024, 6, 1).atStartOfDay())
                        .build())
                .amount(10_000)
                .createdAt(LocalDate.of(2024, 6, 1).atStartOfDay())
                .modifiedAt(LocalDate.of(2024, 6, 1).atStartOfDay())
                .status(OrderStatus.ORDERED)
                .build();

        //when
        boolean result = order.hasSameBuyerAs(1);

        //then
        assertThat(result).isTrue();
    }

    @Test
    void 주문을_취소할_수_있따() {
        long createdDate = ClockUtil.millisFrom(LocalDate.of(2024, 6, 1).atStartOfDay());
        Order order = Order.builder()
                .id(1L)
                .buyer(User.builder()
                        .id(1L)
                        .email("tmfrl4710@gmail.com")
                        .nickname("olivejua")
                        .status(UserStatus.ACTIVE)
                        .createdAt(createdDate)
                        .modifiedAt(createdDate)
                        .build())
                .product(Product.builder()
                        .id(1L)
                        .name("자바의 신")
                        .price(10_000)
                        .createdAt(LocalDate.of(2024, 6, 1).atStartOfDay())
                        .build())
                .amount(10_000)
                .createdAt(LocalDate.of(2024, 6, 1).atStartOfDay())
                .modifiedAt(LocalDate.of(2024, 6, 1).atStartOfDay())
                .status(OrderStatus.ORDERED)
                .build();

        //when
        LocalDateTime now = LocalDateTime.now();
        order = order.cancel(new TestDateTimeHolder(now));

        //then
        assertThat(order.getStatus()).isEqualTo(OrderStatus.CANCELED);
        assertThat(order.getModifiedAt()).isEqualTo(now);
    }

    @Test
    void 주문취소요청시_주문이_취소상태이면_예외를_던진다() {
        //given
        long createdDate = ClockUtil.millisFrom(LocalDate.of(2024, 6, 1).atStartOfDay());
        Order order = Order.builder()
                .id(1L)
                .buyer(User.builder()
                        .id(1L)
                        .email("tmfrl4710@gmail.com")
                        .nickname("olivejua")
                        .status(UserStatus.ACTIVE)
                        .createdAt(createdDate)
                        .modifiedAt(createdDate)
                        .build())
                .product(Product.builder()
                        .id(1L)
                        .name("자바의 신")
                        .price(10_000)
                        .createdAt(LocalDate.of(2024, 6, 1).atStartOfDay())
                        .build())
                .amount(10_000)
                .createdAt(LocalDate.of(2024, 6, 1).atStartOfDay())
                .modifiedAt(LocalDate.of(2024, 6, 1).atStartOfDay())
                .status(OrderStatus.CANCELED)
                .build();

        //when
        //then
        assertThatThrownBy(() -> order.cancel(new TestDateTimeHolder(LocalDateTime.now())))
                .isInstanceOf(IllegalStatusException.class)
                .hasMessage("이미 주문취소 상태입니다.");
    }
}