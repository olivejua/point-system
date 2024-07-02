package dev.olivejua.pointsystem.point.domain;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum PointAccrualType {
    ATTENDANCE_BONUS("출석체크 포인트"),
    CONSECUTIVE_ATTENDANCE_BONUS("10일 연속 출석체크 추가포인트"),
    REVIEW_BONUS("리뷰작성 포인트"),
    JOIN_BONUS("가입축하 포인트"),
    ORDER_BONUS("주문보상 포인트"),
    ;

    private final String name;
}
