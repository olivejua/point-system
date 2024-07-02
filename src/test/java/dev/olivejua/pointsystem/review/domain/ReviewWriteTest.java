package dev.olivejua.pointsystem.review.domain;

import dev.olivejua.pointsystem.common.util.ClockUtil;
import dev.olivejua.pointsystem.order.domain.Order;
import dev.olivejua.pointsystem.order.domain.OrderStatus;
import dev.olivejua.pointsystem.product.domain.Product;
import dev.olivejua.pointsystem.user.domain.User;
import dev.olivejua.pointsystem.user.domain.UserStatus;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.NullAndEmptySource;

import java.time.LocalDate;

import static org.assertj.core.api.Assertions.assertThat;

class ReviewWriteTest {

    @Test
    void 작성자와_구매자가_동일유저라면_true를_반환한다() {
        //given
        User writer = User.builder()
                .id(1L)
                .email("tmfrl4710@gmail.com")
                .nickname("olivejua")
                .status(UserStatus.ACTIVE)
                .createdAt(ClockUtil.millisFrom(LocalDate.of(2024, 6, 1).atStartOfDay()))
                .modifiedAt(ClockUtil.millisFrom(LocalDate.of(2024, 6, 1).atStartOfDay()))
                .build();

        Order order = Order.builder()
                .id(1L)
                .buyer(writer)
                .product(Product.builder()
                        .id(1L)
                        .name("자바의 신")
                        .price(20_000)
                        .createdAt(LocalDate.of(2024, 6, 1).atStartOfDay())
                        .build())
                .amount(20_000)
                .createdAt(LocalDate.of(2024, 6, 1).atStartOfDay())
                .modifiedAt(LocalDate.of(2024, 6, 1).atStartOfDay())
                .status(OrderStatus.ORDERED)
                .build();

        ReviewWrite reviewWrite = new ReviewWrite(order, writer, "Nice Book", "내용");

        //when
        boolean result = reviewWrite.isSameWriterAsBuyer();

        //then
        assertThat(result).isTrue();
    }

    @Test
    void 작성자와_구매자가_동일유저가_아니라면_false를_반환한다() {
        //given
        User writer = User.builder()
                .id(1L)
                .email("tmfrl4710@gmail.com")
                .nickname("olivejua")
                .status(UserStatus.ACTIVE)
                .createdAt(ClockUtil.millisFrom(LocalDate.of(2024, 6, 1).atStartOfDay()))
                .modifiedAt(ClockUtil.millisFrom(LocalDate.of(2024, 6, 1).atStartOfDay()))
                .build();

        Order order = Order.builder()
                .id(1L)
                .buyer(User.builder()
                        .id(2L)
                        .email("tmfrl4710@naver.com")
                        .nickname("seulki")
                        .status(UserStatus.ACTIVE)
                        .createdAt(ClockUtil.millisFrom(LocalDate.of(2024, 6, 1).atStartOfDay()))
                        .modifiedAt(ClockUtil.millisFrom(LocalDate.of(2024, 6, 1).atStartOfDay()))
                        .build())
                .product(Product.builder()
                        .id(1L)
                        .name("자바의 신")
                        .price(20_000)
                        .createdAt(LocalDate.of(2024, 6, 1).atStartOfDay())
                        .build())
                .amount(20_000)
                .createdAt(LocalDate.of(2024, 6, 1).atStartOfDay())
                .modifiedAt(LocalDate.of(2024, 6, 1).atStartOfDay())
                .status(OrderStatus.ORDERED)
                .build();

        ReviewWrite reviewWrite = new ReviewWrite(order, writer, "Nice Book", "내용");

        //when
        boolean result = reviewWrite.isSameWriterAsBuyer();

        //then
        assertThat(result).isFalse();
    }

    @Test
    void 주문건의_orderId를_반환한다() {
        //given
        User writer = User.builder()
                .id(1L)
                .email("tmfrl4710@gmail.com")
                .nickname("olivejua")
                .status(UserStatus.ACTIVE)
                .createdAt(ClockUtil.millisFrom(LocalDate.of(2024, 6, 1).atStartOfDay()))
                .modifiedAt(ClockUtil.millisFrom(LocalDate.of(2024, 6, 1).atStartOfDay()))
                .build();

        Order order = Order.builder()
                .id(1L)
                .buyer(writer)
                .product(Product.builder()
                        .id(1L)
                        .name("자바의 신")
                        .price(20_000)
                        .createdAt(LocalDate.of(2024, 6, 1).atStartOfDay())
                        .build())
                .amount(20_000)
                .createdAt(LocalDate.of(2024, 6, 1).atStartOfDay())
                .modifiedAt(LocalDate.of(2024, 6, 1).atStartOfDay())
                .status(OrderStatus.ORDERED)
                .build();

        ReviewWrite reviewWrite = new ReviewWrite(order, writer, "Nice Book", "내용");

        //when
        long orderId = reviewWrite.getOrderId();

        //then
        assertThat(orderId).isEqualTo(order.getId());
    }

    @Test
    void 구매자의_유저ID를_반환한다() {
        //given
        User writer = User.builder()
                .id(1L)
                .email("tmfrl4710@gmail.com")
                .nickname("olivejua")
                .status(UserStatus.ACTIVE)
                .createdAt(ClockUtil.millisFrom(LocalDate.of(2024, 6, 1).atStartOfDay()))
                .modifiedAt(ClockUtil.millisFrom(LocalDate.of(2024, 6, 1).atStartOfDay()))
                .build();

        Order order = Order.builder()
                .id(1L)
                .buyer(writer)
                .product(Product.builder()
                        .id(1L)
                        .name("자바의 신")
                        .price(20_000)
                        .createdAt(LocalDate.of(2024, 6, 1).atStartOfDay())
                        .build())
                .amount(20_000)
                .createdAt(LocalDate.of(2024, 6, 1).atStartOfDay())
                .modifiedAt(LocalDate.of(2024, 6, 1).atStartOfDay())
                .status(OrderStatus.ORDERED)
                .build();

        ReviewWrite reviewWrite = new ReviewWrite(order, writer, "Nice Book", "내용");

        //when
        long writerId = reviewWrite.getWriterId();

        //then
        assertThat(writerId).isEqualTo(writer.getId());
    }

    @ParameterizedTest
    @NullAndEmptySource
    void 리뷰제목이_Null이거나_빈값이라면_true를_반환한다(String title) {
        //given
        User writer = User.builder()
                .id(1L)
                .email("tmfrl4710@gmail.com")
                .nickname("olivejua")
                .status(UserStatus.ACTIVE)
                .createdAt(ClockUtil.millisFrom(LocalDate.of(2024, 6, 1).atStartOfDay()))
                .modifiedAt(ClockUtil.millisFrom(LocalDate.of(2024, 6, 1).atStartOfDay()))
                .build();

        Order order = Order.builder()
                .id(1L)
                .buyer(writer)
                .product(Product.builder()
                        .id(1L)
                        .name("자바의 신")
                        .price(20_000)
                        .createdAt(LocalDate.of(2024, 6, 1).atStartOfDay())
                        .build())
                .amount(20_000)
                .createdAt(LocalDate.of(2024, 6, 1).atStartOfDay())
                .modifiedAt(LocalDate.of(2024, 6, 1).atStartOfDay())
                .status(OrderStatus.ORDERED)
                .build();

        ReviewWrite reviewWrite = new ReviewWrite(order, writer, title, "내용");

        //when
        boolean result = reviewWrite.hasInvalidTitleOrContent();

        //then
        assertThat(result).isTrue();
    }

    @ParameterizedTest
    @NullAndEmptySource
    void 리뷰내용이_Null이거나_빈값이라면_true를_반환한다(String content) {
        //given
        User writer = User.builder()
                .id(1L)
                .email("tmfrl4710@gmail.com")
                .nickname("olivejua")
                .status(UserStatus.ACTIVE)
                .createdAt(ClockUtil.millisFrom(LocalDate.of(2024, 6, 1).atStartOfDay()))
                .modifiedAt(ClockUtil.millisFrom(LocalDate.of(2024, 6, 1).atStartOfDay()))
                .build();

        Order order = Order.builder()
                .id(1L)
                .buyer(writer)
                .product(Product.builder()
                        .id(1L)
                        .name("자바의 신")
                        .price(20_000)
                        .createdAt(LocalDate.of(2024, 6, 1).atStartOfDay())
                        .build())
                .amount(20_000)
                .createdAt(LocalDate.of(2024, 6, 1).atStartOfDay())
                .modifiedAt(LocalDate.of(2024, 6, 1).atStartOfDay())
                .status(OrderStatus.ORDERED)
                .build();

        ReviewWrite reviewWrite = new ReviewWrite(order, writer, "Nice Book", content);

        //when
        boolean result = reviewWrite.hasInvalidTitleOrContent();

        //then
        assertThat(result).isTrue();
    }

    @Test
    void 리뷰_제목과_내용에_문자가_1개이상_포함되어_있다면_false를_반환한다() {
        //given
        User writer = User.builder()
                .id(1L)
                .email("tmfrl4710@gmail.com")
                .nickname("olivejua")
                .status(UserStatus.ACTIVE)
                .createdAt(ClockUtil.millisFrom(LocalDate.of(2024, 6, 1).atStartOfDay()))
                .modifiedAt(ClockUtil.millisFrom(LocalDate.of(2024, 6, 1).atStartOfDay()))
                .build();

        Order order = Order.builder()
                .id(1L)
                .buyer(writer)
                .product(Product.builder()
                        .id(1L)
                        .name("자바의 신")
                        .price(20_000)
                        .createdAt(LocalDate.of(2024, 6, 1).atStartOfDay())
                        .build())
                .amount(20_000)
                .createdAt(LocalDate.of(2024, 6, 1).atStartOfDay())
                .modifiedAt(LocalDate.of(2024, 6, 1).atStartOfDay())
                .status(OrderStatus.ORDERED)
                .build();

        ReviewWrite reviewWrite = new ReviewWrite(order, writer, "Nice Book", "내용");

        //when
        boolean result = reviewWrite.hasInvalidTitleOrContent();

        //then
        assertThat(result).isFalse();
    }
}