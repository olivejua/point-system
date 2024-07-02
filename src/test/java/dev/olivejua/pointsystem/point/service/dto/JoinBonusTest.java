package dev.olivejua.pointsystem.point.service.dto;

import dev.olivejua.pointsystem.common.util.ClockUtil;
import dev.olivejua.pointsystem.mock.FakePointTransactionRepository;
import dev.olivejua.pointsystem.point.domain.PointAccrualType;
import dev.olivejua.pointsystem.point.domain.PointTransaction;
import dev.olivejua.pointsystem.point.domain.PointTransactionType;
import dev.olivejua.pointsystem.user.domain.User;
import dev.olivejua.pointsystem.user.domain.UserStatus;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;

import static org.assertj.core.api.Assertions.assertThat;

class JoinBonusTest {
    private FakePointTransactionRepository pointTransactionRepository;

    @BeforeEach
    void setUp() {
        pointTransactionRepository = new FakePointTransactionRepository();
    }

    @Test
    void 유저의_가입일이_오늘일자와_같고_가입포인트를_받은적이_없다면_true를_반환한다() {
        //given
        long createdDate = ClockUtil.toMillis(LocalDate.now().atStartOfDay());
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
        boolean result = joinBonus.isEligibleUserForPoints(pointTransactionRepository);

        //then
        assertThat(result).isTrue();
    }

    @Test
    void 유저의_가입일이_오늘일자가_아니라면_false를_반환한다() {
        //given
        long createdDate = ClockUtil.toMillis(LocalDate.now().minusDays(3).atStartOfDay());
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
        assertThat(result).isFalse();
    }

    @Test
    void 유저의_가입_적립내역이_이미_존재하면_false를_반환한다() {
        //given
        long createdDate = ClockUtil.toMillis(LocalDate.now().atStartOfDay());
        User user = User.builder()
                .id(1L)
                .email("tmfrl4710@gmail.com")
                .nickname("olivejua")
                .status(UserStatus.ACTIVE)
                .createdAt(createdDate)
                .modifiedAt(createdDate)
                .build();

        pointTransactionRepository.save(PointTransaction.builder()
                .user(user)
                .type(PointTransactionType.ACCRUAL)
                .accrualType(PointAccrualType.JOIN_BONUS)
                .amount(1_000)
                .createdAt(createdDate)
                .build());

        JoinBonus joinBonus = new JoinBonus(user);

        //when
        boolean result = joinBonus.isEligibleUserForPoints(pointTransactionRepository);

        //then
        assertThat(result).isFalse();
    }

    @Test
    void 고정금액_1_000포인트를_적립금액으로_반환한다() {
        //given
        long createdDate = ClockUtil.toMillis(LocalDate.now().atStartOfDay());
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
        long amount = joinBonus.getAmount();

        //then
        assertThat(amount).isEqualTo(1_000);
    }
}