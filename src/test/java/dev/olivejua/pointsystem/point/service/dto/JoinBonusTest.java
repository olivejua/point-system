package dev.olivejua.pointsystem.point.service.dto;

import dev.olivejua.pointsystem.user.domain.User;
import dev.olivejua.pointsystem.user.domain.UserStatus;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;

import static org.assertj.core.api.Assertions.assertThat;

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

        LocalDate createdDate = LocalDate.now().minusDays(3);
        User user = User.builder()
                .id(1L)
                .email("tmfrl4710@gmail.com")
                .nickname("olivejua")
                .status(UserStatus.ACTIVE)
                .createdAt(createdDate.atStartOfDay())
                .modifiedAt(createdDate.atStartOfDay())
                .build();

        JoinBonus joinBonus = new JoinBonus(user);

        //when
        boolean result = joinBonus.isEligibleUserForPoints(null);

        //then
        assertThat(result).isFalse();
    }

    @Test
    void 유저의_가입_적립내역이_이미_존재하면_false를_반환한다() {
        //given

        //when

        //then
    }
}