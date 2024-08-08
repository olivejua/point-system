package dev.olivejua.pointsystem.user.controller;

import dev.olivejua.pointsystem.common.util.ClockUtil;
import dev.olivejua.pointsystem.mock.TestContainer;
import dev.olivejua.pointsystem.point.domain.PointAccrualType;
import dev.olivejua.pointsystem.point.domain.UserPoint;
import dev.olivejua.pointsystem.user.domain.User;
import dev.olivejua.pointsystem.user.domain.UserCreate;
import dev.olivejua.pointsystem.user.domain.UserStatus;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;

import java.time.Clock;
import java.time.LocalDate;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

class UserControllerTest {
    private long now = Clock.systemDefaultZone().millis();

    @Test
    void 사용자는_회원가입할_수_있다() {
        //given
        TestContainer testContainer = TestContainer.builder()
                .clockHolder(() -> now)
                .build();

        UserCreate userCreate = new UserCreate("tmfrl4710@gmail.com", "olivejua");

        //when
        ResponseEntity<User> result = testContainer.userController.join(userCreate);

        //then
        assertThat(result.getStatusCode()).isEqualTo(HttpStatusCode.valueOf(201));
        assertThat(result.getBody()).isNotNull();
        assertThat(result.getBody().getId()).isEqualTo(1);
        assertThat(result.getBody().getEmail()).isEqualTo("tmfrl4710@gmail.com");
        assertThat(result.getBody().getNickname()).isEqualTo("olivejua");
        assertThat(result.getBody().getStatus()).isEqualTo(UserStatus.ACTIVE);
        assertThat(result.getBody().getCreatedAt()).isEqualTo(now);
    }

    @Test
    void 가입시_회원가입_포인트가_적립된다() {
        //given
        TestContainer testContainer = TestContainer.builder()
                .clockHolder(() -> now)
                .build();

        UserCreate userCreate = new UserCreate("tmfrl4710@gmail.com", "olivejua");

        //when
        ResponseEntity<User> response = testContainer.userController.join(userCreate);

        //then
        assertThat(response.getBody()).isNotNull();
        User user = response.getBody();
        assertThat(testContainer.pointTransactionRepository.existsByUserIdAndAccrualType(user.getId(), PointAccrualType.JOIN_BONUS)).isTrue();

        Optional<UserPoint> userPoint = testContainer.userPointRepository.findByUserId(user.getId());
        assertThat(userPoint.isPresent()).isTrue();
        assertThat(userPoint.get().getUser().isSameAs(user)).isTrue();
        assertThat(userPoint.get().getAmount()).isEqualTo(1_000);
        assertThat(userPoint.get().getCreatedAt()).isEqualTo(now);
        assertThat(userPoint.get().getModifiedAt()).isEqualTo(now);
    }

    @Test
    void 사용자는_로그인할_수_있다() {
        //given
        TestContainer testContainer = TestContainer.builder()
                .clockHolder(() -> now)
                .build();

        long createdAt = ClockUtil.toMillis(LocalDate.of(2024, 6, 1).atStartOfDay());
        testContainer.userRepository.save(User.builder()
                .id(1L)
                .email("tmfrl4710@gmail.com")
                .nickname("olivejua")
                .status(UserStatus.ACTIVE)
                .createdAt(createdAt)
                .modifiedAt(createdAt)
                .build());

        //when
        ResponseEntity<Void> result = testContainer.userController.login("tmfrl4710@gmail.com");

        //then
        assertThat(result.getStatusCode()).isEqualTo(HttpStatusCode.valueOf(204));
        assertThat(result.hasBody()).isFalse();
        assertThat(testContainer.userRepository.getById(1L).getLastLoginAt()).isEqualTo(now);
    }

    @Test
    void 로그인시_출석체크_포인트가_적립된다() {
        //given
        TestContainer testContainer = TestContainer.builder()
                .clockHolder(() -> now)
                .build();

        long createdAt = ClockUtil.toMillis(LocalDate.of(2024, 6, 1).atStartOfDay());
        testContainer.userRepository.save(User.builder()
                .id(1L)
                .email("tmfrl4710@gmail.com")
                .nickname("olivejua")
                .status(UserStatus.ACTIVE)
                .createdAt(createdAt)
                .modifiedAt(createdAt)
                .build());

        //when
        ResponseEntity<Void> result = testContainer.userController.login("tmfrl4710@gmail.com");

        //then
        assertThat(result.getStatusCode()).isEqualTo(HttpStatus.NO_CONTENT);

        assertThat(testContainer.pointTransactionRepository.existsByUserIdAndAccrualType(1L, PointAccrualType.ATTENDANCE_BONUS)).isTrue();

        Optional<UserPoint> userPointOptional = testContainer.userPointRepository.findByUserId(1L);
        assertThat(userPointOptional).isPresent();
        UserPoint userPoint = userPointOptional.get();
        assertThat(userPoint.getAmount()).isEqualTo(10);
    }
}
