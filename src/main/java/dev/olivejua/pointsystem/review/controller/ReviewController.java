package dev.olivejua.pointsystem.review.controller;

import dev.olivejua.pointsystem.point.service.PointService;
import dev.olivejua.pointsystem.review.service.ReviewService;
import lombok.Builder;

@Builder
public class ReviewController {
    private final ReviewService reviewService;
    private final PointService pointService;

//    public ResponseEntity<Review> write(ReviewWriteRequest request) {
//        ReviewWrite reviewWrite = reviewWriteCreateService.create(request);
//        Review review = reviewService.write(reviewWrite);
//        pointService.accrue(new ReviewBonus(review.getWriter()));
//
//        return ResponseEntity.ok(review);
//    }
}
