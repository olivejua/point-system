package dev.olivejua.pointsystem.point.infrastructure;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.time.LocalDateTime;

@RequiredArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
@Table(name = "points")
@Entity
public class PointEntity {

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
