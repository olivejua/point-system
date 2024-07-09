package dev.olivejua.pointsystem.mock;

import dev.olivejua.pointsystem.common.service.ClockHolder;
import dev.olivejua.pointsystem.point.service.PointService;
import dev.olivejua.pointsystem.point.service.port.PointTransactionRepository;
import dev.olivejua.pointsystem.point.service.port.UserPointRepository;
import dev.olivejua.pointsystem.user.controller.UserController;
import dev.olivejua.pointsystem.user.service.UserService;
import dev.olivejua.pointsystem.user.service.port.UserRepository;
import lombok.Builder;

public class TestContainer {
    public final UserController userController;

    public final UserService userService;
    public final PointService pointService;

    public final UserRepository userRepository;
    public final PointTransactionRepository pointTransactionRepository;
    public final UserPointRepository userPointRepository;


    @Builder
    public TestContainer(ClockHolder clockHolder) {
        this.userRepository = new FakeUserRepository();
        this.pointTransactionRepository = new FakePointTransactionRepository();
        this.userPointRepository = new FakeUserPointRepository();

        this.userService = UserService.builder()
                .userRepository(userRepository)
                .clockHolder(clockHolder)
                .build();
        this.pointService = PointService.builder()
                .pointTransactionRepository(pointTransactionRepository)
                .userPointRepository(userPointRepository)
                .clockHolder(clockHolder)
                .build();

        this.userController = UserController.builder()
                .userService(userService)
                .pointService(pointService)
                .clockHolder(clockHolder)
                .build();
    }
}
