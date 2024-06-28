package dev.olivejua.pointsystem.review.domain;

import dev.olivejua.pointsystem.common.exception.InvalidAttributeFormatException;
import dev.olivejua.pointsystem.common.service.DateTimeHolder;
import dev.olivejua.pointsystem.order.domain.Order;
import dev.olivejua.pointsystem.order.service.BuyerDoesNotMatchException;
import dev.olivejua.pointsystem.user.domain.User;
import lombok.Builder;

import java.time.LocalDateTime;

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
