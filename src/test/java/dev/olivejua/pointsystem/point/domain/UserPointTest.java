package dev.olivejua.pointsystem.point.domain;

import dev.olivejua.pointsystem.common.util.ClockUtil;
import dev.olivejua.pointsystem.mock.TestClockHolder;
import dev.olivejua.pointsystem.user.domain.User;
import dev.olivejua.pointsystem.user.domain.UserStatus;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.time.LocalDateTime;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

class UserPointTest {

    @Test
    void User로_초기상태의_객체를_생성할_수_있다() {
        //given
        User user = User.builder()
                .id(1L)
                .email("tmfrl4710@gmail.com")
                .nickname("olivejua")
                .status(UserStatus.ACTIVE)
                .createdAt(ClockUtil.toMillis(LocalDate.of(2024, 6, 1).atStartOfDay()))
                .modifiedAt(ClockUtil.toMillis(LocalDate.of(2024, 6, 1).atStartOfDay()))
                .build();

        //when
        long millis = ClockUtil.toMillis(LocalDateTime.now());
        UserPoint userPoint = UserPoint.initialFrom(user, new TestClockHolder(millis));

        //then
        assertThat(userPoint).isNotNull();
        assertThat(userPoint.getId()).isNull();
        assertThat(userPoint.getUser().isSameAs(user)).isTrue();
        assertThat(userPoint.getAmount()).isEqualTo(0);
        assertThat(userPoint.getCreatedAt()).isEqualTo(millis);
        assertThat(userPoint.getModifiedAt()).isEqualTo(millis);
    }

    @Test
    void 유저포인트에서_전달받은_금액을_더한_유저포인트를_반환한다() {
        //given
        User user = User.builder()
                .id(1L)
                .email("tmfrl4710@gmail.com")
                .nickname("olivejua")
                .status(UserStatus.ACTIVE)
                .createdAt(ClockUtil.toMillis(LocalDate.of(2024, 6, 1).atStartOfDay()))
                .modifiedAt(ClockUtil.toMillis(LocalDate.of(2024, 6, 1).atStartOfDay()))
                .build();

        UserPoint userPoint = UserPoint.initialFrom(user, new TestClockHolder(ClockUtil.toMillis(LocalDate.of(2024, 6, 1).atStartOfDay())));

        long now = ClockUtil.toMillis(LocalDateTime.now());

        //when
        UserPoint result = userPoint.add(1_000, new TestClockHolder(now));

        //then
        assertThat(result).isNotNull();
        assertThat(result.getId()).isEqualTo(userPoint.getId());
        assertThat(result.getUser()).isEqualTo(userPoint.getUser());
        assertThat(result.getAmount()).isEqualTo(1_000);
        assertThat(result.getCreatedAt()).isEqualTo(userPoint.getCreatedAt());
        assertThat(result.getModifiedAt()).isEqualTo(now);
    }

    @Test
    void 유저포인트에서_더할_금액이_0이라면_기존객체를_그대로_반환한다() {
        //given
        User user = User.builder()
                .id(1L)
                .email("tmfrl4710@gmail.com")
                .nickname("olivejua")
                .status(UserStatus.ACTIVE)
                .createdAt(ClockUtil.toMillis(LocalDate.of(2024, 6, 1).atStartOfDay()))
                .modifiedAt(ClockUtil.toMillis(LocalDate.of(2024, 6, 1).atStartOfDay()))
                .build();

        UserPoint userPoint = UserPoint.initialFrom(user, new TestClockHolder(ClockUtil.toMillis(LocalDate.of(2024, 6, 1).atStartOfDay())));

        long now = ClockUtil.toMillis(LocalDateTime.now());

        //when
        UserPoint result = userPoint.add(0, new TestClockHolder(now));

        //then
        assertThat(result).isEqualTo(userPoint);
    }
}