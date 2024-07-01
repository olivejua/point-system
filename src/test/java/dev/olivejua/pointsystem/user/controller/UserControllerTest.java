package dev.olivejua.pointsystem.user.controller;

import dev.olivejua.pointsystem.mock.FakePointTransactionRepository;
import dev.olivejua.pointsystem.mock.FakeUserPointRepository;
import dev.olivejua.pointsystem.mock.FakeUserRepository;
import dev.olivejua.pointsystem.mock.TestDateTimeHolder;
import dev.olivejua.pointsystem.point.domain.UserPoint;
import dev.olivejua.pointsystem.point.service.PointService;
import dev.olivejua.pointsystem.user.domain.User;
import dev.olivejua.pointsystem.user.domain.UserCreate;
import dev.olivejua.pointsystem.user.service.UserService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.http.ResponseEntity;

import java.time.Clock;
import java.time.LocalDateTime;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

class UserControllerTest {
    private UserController userController;
    private FakeUserPointRepository fakeUserPointRepository;

    private LocalDateTime now = LocalDateTime.now();
    private long millis = Clock.systemUTC().millis();

    @BeforeEach
    void setUp() {
        fakeUserPointRepository = new FakeUserPointRepository();

        userController = UserController.builder()
                .userService(UserService.builder()
                        .userRepository(new FakeUserRepository())
                        .dateTimeHolder(new TestDateTimeHolder(now))
                        .build())
                .pointService(PointService.builder()
                        .userPointRepository(fakeUserPointRepository)
                        .pointTransactionRepository(new FakePointTransactionRepository())
                        .clockHolder(() -> millis)
                        .dateTimeHolder(new TestDateTimeHolder(now))
                        .build())
                .build();
    }

    @Test
    void 가입시_회원가입_포인트가_적립된다() {
        //given
        UserCreate userCreate = new UserCreate("tmfrl4710@gmail.com", "olivejua");

        //when
        ResponseEntity<User> response = userController.join(userCreate);
        User user = response.getBody();

        //then
        Optional<UserPoint> userPoint = fakeUserPointRepository.findByUserId(user.getId());
        assertThat(userPoint.isPresent()).isTrue();
        assertThat(userPoint.get().getUser().isSameAs(user)).isTrue();
        assertThat(userPoint.get().getAmount()).isEqualTo(1_000);
        assertThat(userPoint.get().getCreatedAt()).isEqualTo(now);
        assertThat(userPoint.get().getModifiedAt()).isEqualTo(now);
    }

    @Test
    void 로그인시_출석체크_포인트가_적립된다() {
        //given

        //when

        //then
    }
}