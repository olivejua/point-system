package dev.olivejua.pointsystem.point.service.dto;

import dev.olivejua.pointsystem.common.util.ClockUtil;
import dev.olivejua.pointsystem.user.domain.User;
import dev.olivejua.pointsystem.user.domain.UserStatus;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;

class JoinBonusTest {

    @Test
    void 유저의_가입일이_오늘일자와_같고_가입포인트를_받은적이_없다면_true를_반환한다() {
        //given

        //when

        //then
    }

    @Test
    void 유저의_가입일이_오늘일자가_아니라면_false를_반환한다() {
        //given

        long createdDate = ClockUtil.millisFrom(LocalDate.now().minusDays(3).atStartOfDay());
        User user = User.builder()
                .id(1L)
                .email("tmfrl4710@gmail.com")
                .nickname("olivejua")
                .status(UserStatus.ACTIVE)
                .createdAt(createdDate)
                .modifiedAt(createdDate)
                .build();

        JoinBonus joinBonus = new JoinBonus(user);

        //when
        boolean result = joinBonus.isEligibleUserForPoints(null);

        //then
//        assertThat(result).isFalse();
    }

    @Test
    void 유저의_가입_적립내역이_이미_존재하면_false를_반환한다() {
        //given

        //when

        //then
    }
}