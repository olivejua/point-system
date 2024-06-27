package dev.olivejua.pointsystem.point.domain;

import java.time.LocalDateTime;

public class PointAccrualType {
    private long id;
    private String code;
    private String name;
    private boolean isPercentage;
    private long fixedAmount;
    private long percentageRate;
    private String description;
    private LocalDateTime createdAt;
    private LocalDateTime modifiedAt;
}
