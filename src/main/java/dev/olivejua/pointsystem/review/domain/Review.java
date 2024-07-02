package dev.olivejua.pointsystem.review.domain;

import dev.olivejua.pointsystem.common.exception.IllegalStatusException;
import dev.olivejua.pointsystem.common.exception.InvalidAttributeFormatException;
import dev.olivejua.pointsystem.common.service.DateTimeHolder;
import dev.olivejua.pointsystem.order.domain.Order;
import dev.olivejua.pointsystem.common.exception.BuyerDoesNotMatchException;
import dev.olivejua.pointsystem.user.domain.User;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
@Builder
public class Review {
    private Long id;
    private Order order;
    private User writer;
    private String title;
    private String content;
    private LocalDateTime createdAt;
    private LocalDateTime modifiedAt;

    public static Review from(ReviewWrite reviewWrite, DateTimeHolder dateTimeHolder) {
        if (!reviewWrite.isSameWriterAsBuyer()) {
            throw new BuyerDoesNotMatchException();
        }

        if (reviewWrite.hasInvalidTitleOrContent()) {
            throw new InvalidAttributeFormatException("리뷰의 제목 또는 내용");
        }

        if (reviewWrite.hasOrderWithCancellation()) {
            throw new IllegalStatusException("취소상태의 주문에 리뷰를 작성할 수 없습니다.");
        }

        if (reviewWrite.hasOrderCreatedBeforeThanAMonth(dateTimeHolder)) {
            throw new IllegalStateException("1개월 이전 주문건은 리뷰를 작성할 수 없습니다.");
        }

        LocalDateTime now = dateTimeHolder.now();
        return Review.builder()
                .order(reviewWrite.getOrder())
                .writer(reviewWrite.getWriter())
                .title(reviewWrite.getTitle())
                .content(reviewWrite.getContent())
                .createdAt(now)
                .modifiedAt(now)
                .build();
    }
}
