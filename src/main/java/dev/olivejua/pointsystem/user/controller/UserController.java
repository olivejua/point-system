package dev.olivejua.pointsystem.user.controller;

import dev.olivejua.pointsystem.common.service.ClockHolder;
import dev.olivejua.pointsystem.point.domain.accrualbonus.AttendanceBonus;
import dev.olivejua.pointsystem.point.domain.accrualbonus.JoinBonus;
import dev.olivejua.pointsystem.point.service.PointService;
import dev.olivejua.pointsystem.user.domain.User;
import dev.olivejua.pointsystem.user.domain.UserCreate;
import dev.olivejua.pointsystem.user.service.UserService;
import lombok.Builder;
import org.springframework.http.ResponseEntity;

import java.net.URI;

@Builder
public class UserController {
    private final UserService userService;
    private final PointService pointService;
    private final ClockHolder clockHolder;

    public ResponseEntity<User> join(UserCreate userCreate) {
        User user = userService.join(userCreate);
        pointService.accrue(new JoinBonus(user));

        return ResponseEntity
                .created(URI.create("http://localhost:8080/users/" + user.getId()))
                .body(user);
    }

    public ResponseEntity<Void> login(String email) {
        User user = userService.login(email);
        pointService.accrue(new AttendanceBonus(user, clockHolder));

        return ResponseEntity.noContent()
                .build();
    }
}
