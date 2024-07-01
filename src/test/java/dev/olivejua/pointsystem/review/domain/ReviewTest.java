package dev.olivejua.pointsystem.review.domain;

import dev.olivejua.pointsystem.common.exception.IllegalStatusException;
import dev.olivejua.pointsystem.common.exception.InvalidAttributeFormatException;
import dev.olivejua.pointsystem.mock.TestDateTimeHolder;
import dev.olivejua.pointsystem.order.domain.Order;
import dev.olivejua.pointsystem.order.domain.OrderStatus;
import dev.olivejua.pointsystem.common.exception.BuyerDoesNotMatchException;
import dev.olivejua.pointsystem.product.domain.Product;
import dev.olivejua.pointsystem.user.domain.User;
import dev.olivejua.pointsystem.user.domain.UserStatus;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.NullAndEmptySource;

import java.time.LocalDate;
import java.time.LocalDateTime;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

public class ReviewTest {

    @Test
    void reviewWrite로_객체를_생성할_수_있다() {
        //given
        User writer = User.builder()
                .id(1L)
                .email("tmfrl4710@gmail.com")
                .nickname("olivejua")
                .status(UserStatus.ACTIVE)
                .createdAt(LocalDate.of(2024, 6, 1).atStartOfDay())
                .modifiedAt(LocalDate.of(2024, 6, 1).atStartOfDay())
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
        LocalDateTime now = LocalDateTime.now();

        //when
        Review review = Review.from(reviewWrite, new TestDateTimeHolder(now));

        //then
        assertThat(review).isNotNull();
        assertThat(review.getId()).isNull();
        assertThat(review.getOrder()).isEqualTo(order);
        assertThat(review.getWriter()).isEqualTo(writer);
        assertThat(review.getTitle()).isEqualTo("Nice Book");
        assertThat(review.getContent()).isEqualTo("내용");
        assertThat(review.getCreatedAt()).isEqualTo(now);
        assertThat(review.getModifiedAt()).isEqualTo(now);
    }

    @Test
    void 구매자와_리뷰작성자가_동일유저가_아니라면_예외를_던진다() {
        User writer = User.builder()
                .id(1L)
                .email("tmfrl4710@gmail.com")
                .nickname("olivejua")
                .status(UserStatus.ACTIVE)
                .createdAt(LocalDate.of(2024, 6, 1).atStartOfDay())
                .modifiedAt(LocalDate.of(2024, 6, 1).atStartOfDay())
                .build();

        Order order = Order.builder()
                .id(1L)
                .buyer(User.builder()
                        .id(2L)
                        .email("tmfrl4710@naver.com")
                        .nickname("seulki")
                        .status(UserStatus.ACTIVE)
                        .createdAt(LocalDate.of(2024, 6, 1).atStartOfDay())
                        .modifiedAt(LocalDate.of(2024, 6, 1).atStartOfDay())
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
        //then
        assertThatThrownBy(() -> Review.from(reviewWrite, LocalDateTime::now))
                .isInstanceOf(BuyerDoesNotMatchException.class);
    }

    @ParameterizedTest
    @NullAndEmptySource
    void 리뷰제목이_빈값이라면_예외를_던진다(String title) {
        //given
        User writer = User.builder()
                .id(1L)
                .email("tmfrl4710@gmail.com")
                .nickname("olivejua")
                .status(UserStatus.ACTIVE)
                .createdAt(LocalDate.of(2024, 6, 1).atStartOfDay())
                .modifiedAt(LocalDate.of(2024, 6, 1).atStartOfDay())
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
        //then
        assertThatThrownBy(() -> Review.from(reviewWrite, LocalDateTime::now))
                .isInstanceOf(InvalidAttributeFormatException.class)
                .hasMessageContaining("리뷰의 제목 또는 내용");
    }

    @ParameterizedTest
    @NullAndEmptySource
    void 리뷰내용이_빈값이라면_예외를_던진다(String content) {
        //given
        User writer = User.builder()
                .id(1L)
                .email("tmfrl4710@gmail.com")
                .nickname("olivejua")
                .status(UserStatus.ACTIVE)
                .createdAt(LocalDate.of(2024, 6, 1).atStartOfDay())
                .modifiedAt(LocalDate.of(2024, 6, 1).atStartOfDay())
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
        //then
        assertThatThrownBy(() -> Review.from(reviewWrite, LocalDateTime::now))
                .isInstanceOf(InvalidAttributeFormatException.class)
                .hasMessageContaining("리뷰의 제목 또는 내용");
    }

    @Test
    void 주문상태가_취소상태라면_예외를_던진다() {
        //given
        User writer = User.builder()
                .id(1L)
                .email("tmfrl4710@gmail.com")
                .nickname("olivejua")
                .status(UserStatus.ACTIVE)
                .createdAt(LocalDate.of(2024, 6, 1).atStartOfDay())
                .modifiedAt(LocalDate.of(2024, 6, 1).atStartOfDay())
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
                .status(OrderStatus.CANCELED)
                .build();

        ReviewWrite reviewWrite = new ReviewWrite(order, writer, "Nice Book", "내용");

        //when
        //then
        assertThatThrownBy(() -> Review.from(reviewWrite, LocalDateTime::now))
                .isInstanceOf(IllegalStatusException.class)
                .hasMessage("취소상태의 주문에 리뷰를 작성할 수 없습니다.");
    }
}