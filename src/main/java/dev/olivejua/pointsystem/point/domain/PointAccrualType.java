package dev.olivejua.pointsystem.point.domain;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum PointAccrualType {
    ATTENDANCE_BONUS("출석체크 포인트", false, 10, 0),
    CONSECUTIVE_ATTENDANCE_BONUS("10일 연속 출석체크 추가포인트", false, 100, 0),
    REVIEW_BONUS("리뷰작성 포인트", false, 500, 0),
    JOIN_BONUS("가입축하 포인트", false, 1000, 0),
    ORDER_BONUS("주문보상 포인트", true, 0, 5),
    ;

    private final String name;
    private final boolean isPercentage;
    private final long fixedAmount;
    private final long percentageRate;
}
