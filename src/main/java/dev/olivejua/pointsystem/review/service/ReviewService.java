package dev.olivejua.pointsystem.review.service;

import dev.olivejua.pointsystem.common.exception.ReviewAlreadyExistsException;
import dev.olivejua.pointsystem.common.service.DateTimeHolder;
import dev.olivejua.pointsystem.review.domain.Review;
import dev.olivejua.pointsystem.review.domain.ReviewWrite;
import dev.olivejua.pointsystem.review.service.port.ReviewRepository;
import lombok.Builder;

@Builder
public class ReviewService {
    private final ReviewRepository reviewRepository;
    private final DateTimeHolder dateTimeHolder;

    public Review write(ReviewWrite reviewWrite) {
        if (reviewRepository.existsByOrderIdAndWriterId(reviewWrite.getOrderId(), reviewWrite.getWriterId())) {
            throw new ReviewAlreadyExistsException();
        }

        //TODO 포인트 적립

        Review review = Review.from(reviewWrite, dateTimeHolder);
        return reviewRepository.save(review);
    }
}
