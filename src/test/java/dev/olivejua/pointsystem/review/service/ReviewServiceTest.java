package dev.olivejua.pointsystem.review.service;

import dev.olivejua.pointsystem.common.exception.ReviewAlreadyExistsException;
import dev.olivejua.pointsystem.mock.FakeReviewRepository;
import dev.olivejua.pointsystem.mock.TestDateTimeHolder;
import dev.olivejua.pointsystem.order.domain.Order;
import dev.olivejua.pointsystem.order.domain.OrderStatus;
import dev.olivejua.pointsystem.product.domain.Product;
import dev.olivejua.pointsystem.review.domain.Review;
import dev.olivejua.pointsystem.review.domain.ReviewWrite;
import dev.olivejua.pointsystem.user.domain.User;
import dev.olivejua.pointsystem.user.domain.UserStatus;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.time.LocalDateTime;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

class ReviewServiceTest {
    private ReviewService reviewService;

    private LocalDateTime now = LocalDateTime.now();

    @BeforeEach
    void setUp() {
        FakeReviewRepository reviewRepository = new FakeReviewRepository();
        reviewService = ReviewService.builder()
                .reviewRepository(reviewRepository)
                .dateTimeHolder(new TestDateTimeHolder(now))
                .build();

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
        Review review = Review.builder()
                .order(order)
                .writer(writer)
                .title("Nice!")
                .content("very nice book")
                .createdAt(LocalDate.of(2024, 6, 5).atStartOfDay())
                .modifiedAt(LocalDate.of(2024, 6, 5).atStartOfDay())
                .build();

        reviewRepository.save(review);
    }

    @Test
    void ReviewWrite로_리뷰를_저장할_수_있다() {
        //given
        User writer = User.builder()
                .id(2L)
                .email("tmfrl4710@naver.com")
                .nickname("seulki")
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

        ReviewWrite reviewWrite = new ReviewWrite(order, writer, "Nice Book", "This is very nice!");

        //when
        Review review = reviewService.write(reviewWrite);

        //then
        assertThat(review).isNotNull();
        assertThat(review.getId()).isNotNull();
        assertThat(review.getOrder().getId()).isEqualTo(1L);
        assertThat(review.getWriter().getId()).isEqualTo(writer.getId());
        assertThat(review.getTitle()).isEqualTo(reviewWrite.getTitle());
        assertThat(review.getContent()).isEqualTo(reviewWrite.getContent());
        assertThat(review.getCreatedAt()).isEqualTo(now);
        assertThat(review.getModifiedAt()).isEqualTo(now);
    }

    @Test
    void 동일작성자가_동일주문건을_2개_이상_작성요청하면_예외를_던진다() {
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

        ReviewWrite reviewWrite = new ReviewWrite(order, writer, "Nice Book", "This is very nice!");

        //when
        //then
        assertThatThrownBy(() -> reviewService.write(reviewWrite))
                .isInstanceOf(ReviewAlreadyExistsException.class);
    }
}