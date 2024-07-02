package dev.olivejua.pointsystem.point.service.dto;

import dev.olivejua.pointsystem.mock.FakePointTransactionRepository;
import dev.olivejua.pointsystem.point.domain.PointAccrualType;
import dev.olivejua.pointsystem.point.domain.PointTransaction;
import dev.olivejua.pointsystem.point.domain.PointTransactionType;
import dev.olivejua.pointsystem.point.domain.accrualbonus.ReviewBonus;
import dev.olivejua.pointsystem.user.domain.User;
import dev.olivejua.pointsystem.user.domain.UserStatus;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.time.LocalDateTime;

import static dev.olivejua.pointsystem.common.util.ClockUtil.toMillis;
import static org.assertj.core.api.Assertions.assertThat;

class ReviewBonusTest {
    private FakePointTransactionRepository pointTransactionRepository;

    @BeforeEach
    void setUp() {
        pointTransactionRepository = new FakePointTransactionRepository();
    }

    @Test
    void 리뷰작성_포인트_적립을_동일일자에_3번_이하_받은_사용자는_true를_반환한다() {
        //given
        long createdDate = toMillis(LocalDate.now().atStartOfDay());
        User user = User.builder()
                .id(1L)
                .email("tmfrl4710@gmail.com")
                .nickname("olivejua")
                .status(UserStatus.ACTIVE)
                .createdAt(createdDate)
                .modifiedAt(createdDate)
                .build();

        for (int i = 0; i < 2; i++) {
            pointTransactionRepository.save(PointTransaction.builder()
                    .user(user)
                    .accrualType(PointAccrualType.REVIEW_BONUS)
                    .type(PointTransactionType.ACCRUAL)
                    .amount(500)
                    .createdAt(toMillis(LocalDateTime.now()))
                    .build());
        }

        ReviewBonus reviewBonus = new ReviewBonus(user);

        //when
        boolean result = reviewBonus.isEligibleUserForPoints(pointTransactionRepository);

        //then
        assertThat(result).isTrue();
    }

    @Test
    void 리뷰작성_포인트_적립을_동일일자에_3번_이상_받은_사용자는_false를_반환한다() {
        //given
        long createdDate = toMillis(LocalDate.now().atStartOfDay());
        User user = User.builder()
                .id(1L)
                .email("tmfrl4710@gmail.com")
                .nickname("olivejua")
                .status(UserStatus.ACTIVE)
                .createdAt(createdDate)
                .modifiedAt(createdDate)
                .build();

        for (int i = 0; i < 3; i++) {
            pointTransactionRepository.save(PointTransaction.builder()
                    .user(user)
                    .accrualType(PointAccrualType.REVIEW_BONUS)
                    .type(PointTransactionType.ACCRUAL)
                    .amount(500)
                    .createdAt(toMillis(LocalDateTime.now()))
                    .build());
        }

        ReviewBonus reviewBonus = new ReviewBonus(user);

        //when
        boolean result = reviewBonus.isEligibleUserForPoints(pointTransactionRepository);

        //then
        assertThat(result).isFalse();
    }

    @Test
    void 고정금액_500포인트를_적립금액으로_반환한다() {
        //given
        long createdDate = toMillis(LocalDate.now().atStartOfDay());
        User user = User.builder()
                .id(1L)
                .email("tmfrl4710@gmail.com")
                .nickname("olivejua")
                .status(UserStatus.ACTIVE)
                .createdAt(createdDate)
                .modifiedAt(createdDate)
                .build();

        ReviewBonus reviewBonus = new ReviewBonus(user);

        //when
        long amount = reviewBonus.getAmount();

        //then
        assertThat(amount).isEqualTo(500);
    }
}