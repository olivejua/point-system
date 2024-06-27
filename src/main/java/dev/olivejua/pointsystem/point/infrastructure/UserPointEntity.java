package dev.olivejua.pointsystem.point.infrastructure;

import jakarta.persistence.Column;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

import java.time.LocalDateTime;

public class UserPointEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true, nullable = false)
    private String name;

    @Column(nullable = false)
    private int amount;

    //유저의 현재 사용금액
    //유저의 적립/사용 내역

    @Column(nullable = false)
    private PointStatus status;

    @Column(nullable = false)
    private LocalDateTime createdAt;
}
