package dev.olivejua.pointsystem.point.domain.accrualbonus;

import dev.olivejua.pointsystem.common.util.ClockUtil;
import dev.olivejua.pointsystem.mock.FakePointTransactionRepository;
import dev.olivejua.pointsystem.mock.TestClockHolder;
import dev.olivejua.pointsystem.point.domain.PointAccrualType;
import dev.olivejua.pointsystem.point.domain.PointTransaction;
import dev.olivejua.pointsystem.point.domain.PointTransactionType;
import dev.olivejua.pointsystem.user.domain.User;
import dev.olivejua.pointsystem.user.domain.UserStatus;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.time.LocalDateTime;

import static dev.olivejua.pointsystem.common.util.ClockUtil.toMillis;
import static org.assertj.core.api.Assertions.assertThat;

class AttendanceBonusTest {
    private FakePointTransactionRepository pointTransactionRepository;

    @BeforeEach
    void setUp() {
        pointTransactionRepository = new FakePointTransactionRepository();
    }

    @Test
    void 출석체크_포인트를_오늘일자로_처음_적립하면_true를_리턴한다() {
        //given
        User user = User.builder()
                .id(1L)
                .email("tmfrl4710@gmail.com")
                .nickname("olivejua")
                .status(UserStatus.ACTIVE)
                .createdAt(toMillis(LocalDate.now().atStartOfDay()))
                .modifiedAt(toMillis(LocalDate.now().atStartOfDay()))
                .build();

        LocalDateTime now = LocalDateTime.now();
        AttendanceBonus attendanceBonus = AttendanceBonus.builder()
                .user(user)
                .clockHolder(new TestClockHolder(toMillis(now)))
                .build();

        //when
        boolean result = attendanceBonus.isEligibleUserForPoints(pointTransactionRepository);

        //then
        assertThat(result).isTrue();
    }

    @Test
    void 출석체크_포인트를_동일날짜로_이미_적립내역이_존재한다면_false를_리턴한다() {
        //given
        long userJoinDate = toMillis(LocalDate.now().minusDays(1).atStartOfDay());
        User user = User.builder()
                .id(1L)
                .email("tmfrl4710@gmail.com")
                .nickname("olivejua")
                .status(UserStatus.ACTIVE)
                .createdAt(userJoinDate)
                .modifiedAt(userJoinDate)
                .build();

        LocalDateTime now = LocalDateTime.now();
        pointTransactionRepository.save(PointTransaction.builder()
                .user(user)
                .type(PointTransactionType.ACCRUAL)
                .accrualType(PointAccrualType.ATTENDANCE_BONUS)
                .amount(10)
                .createdAt(toMillis(now.toLocalDate().atStartOfDay()))
                .build());

        AttendanceBonus attendanceBonus = AttendanceBonus.builder()
                .user(user)
                .clockHolder(new TestClockHolder(toMillis(now)))
                .build();

        //when
        boolean result = attendanceBonus.isEligibleUserForPoints(pointTransactionRepository);

        //then
        assertThat(result).isFalse();
    }

    @Test
    void 고정금액_10포인트를_적립금액으로_반환한다() {
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

        AttendanceBonus attendanceBonus = AttendanceBonus.builder()
                .user(user)
                .clockHolder(new TestClockHolder(toMillis(LocalDateTime.now())))
                .build();

        //when
        long amount = attendanceBonus.getAmount();

        //then
        assertThat(amount).isEqualTo(10);
    }
}