package dev.olivejua.pointsystem.point.service;

import dev.olivejua.pointsystem.mock.FakePointTransactionRepository;
import dev.olivejua.pointsystem.mock.FakeUserPointRepository;
import dev.olivejua.pointsystem.mock.TestClockHolder;
import dev.olivejua.pointsystem.point.domain.PointAccrualType;
import dev.olivejua.pointsystem.point.domain.PointTransaction;
import dev.olivejua.pointsystem.point.domain.PointTransactionType;
import dev.olivejua.pointsystem.point.domain.accrualbonus.AttendanceBonus;
import dev.olivejua.pointsystem.point.domain.accrualbonus.JoinBonus;
import dev.olivejua.pointsystem.user.domain.User;
import dev.olivejua.pointsystem.user.domain.UserStatus;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Optional;

import static dev.olivejua.pointsystem.common.util.ClockUtil.toMillis;
import static org.assertj.core.api.Assertions.assertThat;

class PointServiceTest {
    private PointService pointService;

    private long now = toMillis(LocalDateTime.now());

    @BeforeEach
    void setUp() {
        pointService = PointService.builder()
                .userPointRepository(new FakeUserPointRepository())
                .pointTransactionRepository(new FakePointTransactionRepository())
                .clockHolder(new TestClockHolder(now))
                .build();
    }

    @Test
    void 적립대상이_아니라면_적립하지_않고_종료한다() {
        //given
        User user = User.builder()
                .id(1L)
                .email("tmfrl4710@gmail.com")
                .nickname("olivejua")
                .status(UserStatus.ACTIVE)
                .createdAt(toMillis(LocalDate.now().minusDays(1).atStartOfDay()))
                .modifiedAt(toMillis(LocalDate.now().minusDays(1).atStartOfDay()))
                .build();

        //when
        Optional<PointTransaction> result = pointService.accrue(new JoinBonus(user));

        //then
        assertThat(result).isEmpty();
    }

    @Test
    void 회원가입시_1_000포인트를_적립한다() {
        //given
        User user = User.builder()
                .id(1L)
                .email("tmfrl4710@gmail.com")
                .nickname("olivejua")
                .status(UserStatus.ACTIVE)
                .createdAt(toMillis(LocalDate.now().atStartOfDay()))
                .modifiedAt(toMillis(LocalDate.now().atStartOfDay()))
                .build();

        //when
        Optional<PointTransaction> result = pointService.accrue(new JoinBonus(user));

        //then
        assertThat(result).isPresent();
        PointTransaction transaction = result.get();
        assertThat(transaction.getId()).isNotNull();
        assertThat(transaction.getUser().isSameAs(user)).isTrue();
        assertThat(transaction.getType()).isEqualTo(PointTransactionType.ACCRUAL);
        assertThat(transaction.getAccrualType()).isEqualTo(PointAccrualType.JOIN_BONUS);
        assertThat(transaction.getOrder()).isNull();
        assertThat(transaction.getAmount()).isEqualTo(1000L);
        assertThat(transaction.getCreatedAt()).isEqualTo(now);
    }

    @Test
    void 출석체크시_10포인트를_적립한다() {
        //given
        User user = User.builder()
                .id(1L)
                .email("tmfrl4710@gmail.com")
                .nickname("olivejua")
                .status(UserStatus.ACTIVE)
                .createdAt(toMillis(LocalDate.now().atStartOfDay()))
                .modifiedAt(toMillis(LocalDate.now().atStartOfDay()))
                .build();

        //when
        Optional<PointTransaction> result = pointService.accrue(new AttendanceBonus(user, () -> now));

        //then
        assertThat(result).isPresent();
        PointTransaction transaction = result.get();
        assertThat(transaction.getId()).isNotNull();
        assertThat(transaction.getUser().isSameAs(user)).isTrue();
        assertThat(transaction.getType()).isEqualTo(PointTransactionType.ACCRUAL);
        assertThat(transaction.getAccrualType()).isEqualTo(PointAccrualType.ATTENDANCE_BONUS);
        assertThat(transaction.getOrder()).isNull();
        assertThat(transaction.getAmount()).isEqualTo(10L);
        assertThat(transaction.getCreatedAt()).isEqualTo(now);
    }
}