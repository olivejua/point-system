package dev.olivejua.pointsystem.mock;

import dev.olivejua.pointsystem.common.service.DateTimeHolder;
import lombok.RequiredArgsConstructor;

import java.time.LocalDateTime;

@RequiredArgsConstructor
public class TestDateTimeHolder implements DateTimeHolder {
    private final LocalDateTime localDateTime;

    @Override
    public LocalDateTime now() {
        return localDateTime;
    }
}
