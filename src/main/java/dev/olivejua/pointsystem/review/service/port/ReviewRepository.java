package dev.olivejua.pointsystem.review.service.port;

import dev.olivejua.pointsystem.review.domain.Review;

public interface ReviewRepository {

    boolean existsByOrderIdAndWriterId(long orderId, long writerId);

    Review save(Review review);

}
