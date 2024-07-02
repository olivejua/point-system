package dev.olivejua.pointsystem.user.domain;

import dev.olivejua.pointsystem.common.util.ClockUtil;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;

import static org.assertj.core.api.Assertions.assertThat;

public class UserUpdateTest {

    @Test
    void 유저의_이메일과_닉네임_값과_UserUpdate_이메일과_닉네임값이_같다면_true를_반환한다() {
        //given
        User user = User.builder()
                .id(1L)
                .email("tmfrl4710@gmail.com")
                .nickname("olivejua")
                .status(UserStatus.ACTIVE)
                .createdAt(ClockUtil.millisFrom(LocalDate.of(2024, 6, 1).atStartOfDay()))
                .modifiedAt(ClockUtil.millisFrom(LocalDate.of(2024, 6, 1).atStartOfDay()))
                .build();

        UserUpdate userUpdate = new UserUpdate("tmfrl4710@gmail.com", "olivejua");

        //when
        boolean result = userUpdate.hasSameEmailAndNicknameFrom(user);

        //then
        assertThat(result).isTrue();
    }

    @Test
    void 유저의_이메일과_UserUpdate_이메일이_다르다면_false를_반환한다() {
        //given
        User user = User.builder()
                .id(1L)
                .email("tmfrl4710@gmail.com")
                .nickname("olivejua")
                .status(UserStatus.ACTIVE)
                .createdAt(ClockUtil.millisFrom(LocalDate.of(2024, 6, 1).atStartOfDay()))
                .modifiedAt(ClockUtil.millisFrom(LocalDate.of(2024, 6, 1).atStartOfDay()))
                .build();

        UserUpdate userUpdate = new UserUpdate("tmfrl4710@naver.com", "olivejua");

        //when
        boolean result = userUpdate.hasSameEmailAndNicknameFrom(user);

        //then
        assertThat(result).isFalse();
    }

    @Test
    void 유저의_닉네임과_UserUpdate_닉네임이_다르다면_false를_반환한다() {
        //given
        User user = User.builder()
                .id(1L)
                .email("tmfrl4710@gmail.com")
                .nickname("olivejua")
                .status(UserStatus.ACTIVE)
                .createdAt(ClockUtil.millisFrom(LocalDate.of(2024, 6, 1).atStartOfDay()))
                .modifiedAt(ClockUtil.millisFrom(LocalDate.of(2024, 6, 1).atStartOfDay()))
                .build();

        UserUpdate userUpdate = new UserUpdate("tmfrl4710@gmail.com", "seulki");

        //when
        boolean result = userUpdate.hasSameEmailAndNicknameFrom(user);

        //then
        assertThat(result).isFalse();
    }
}