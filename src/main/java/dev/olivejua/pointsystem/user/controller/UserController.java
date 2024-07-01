package dev.olivejua.pointsystem.user.controller;

import dev.olivejua.pointsystem.point.service.dto.AttendanceBonus;
import dev.olivejua.pointsystem.point.service.dto.JoinBonus;
import dev.olivejua.pointsystem.point.service.PointService;
import dev.olivejua.pointsystem.user.domain.User;
import dev.olivejua.pointsystem.user.domain.UserCreate;
import dev.olivejua.pointsystem.user.service.UserService;
import lombok.Builder;
import org.springframework.http.ResponseEntity;

@Builder
public class UserController {
    private final UserService userService;
    private final PointService pointService;

    public ResponseEntity<User> join(UserCreate userCreate) {
        User user = userService.join(userCreate);
        pointService.accrue(new JoinBonus(user));

        return ResponseEntity.ok(user);
    }

    public ResponseEntity<Void> login(String email) {
        User user = userService.login(email);
        pointService.accrue(new AttendanceBonus(user));

        return ResponseEntity.ok()
                .build();
    }
}