package dev.olivejua.pointsystem.common.infrastructure;

import dev.olivejua.pointsystem.common.service.ClockHolder;

import java.time.Clock;

public class SystemClockHolder implements ClockHolder {

    @Override
    public long millis() {
        return Clock.systemUTC().millis();
    }
}
