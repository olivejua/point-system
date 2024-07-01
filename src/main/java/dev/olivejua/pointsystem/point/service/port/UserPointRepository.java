package dev.olivejua.pointsystem.point.service.port;

import dev.olivejua.pointsystem.point.domain.UserPoint;

import java.util.Optional;

public interface UserPointRepository {

    Optional<UserPoint> findByUserId(long userId);

    UserPoint save(UserPoint userPoint);

}
