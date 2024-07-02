package dev.olivejua.pointsystem.review.controller;

import dev.olivejua.pointsystem.point.domain.accrualbonus.ReviewBonus;
import dev.olivejua.pointsystem.review.domain.Review;
import dev.olivejua.pointsystem.review.domain.ReviewWrite;
import dev.olivejua.pointsystem.review.service.ReviewService;
import dev.olivejua.pointsystem.point.service.PointService;
import lombok.Builder;
import org.springframework.http.ResponseEntity;

@Builder
public class ReviewController {
    private final ReviewService reviewService;
    private final PointService pointService;

    public ResponseEntity<Review> write(ReviewWrite reviewWrite) {
        Review review = reviewService.write(reviewWrite);
        pointService.accrue(new ReviewBonus(review.getWriter()));

        return ResponseEntity.ok(review);
    }
}
