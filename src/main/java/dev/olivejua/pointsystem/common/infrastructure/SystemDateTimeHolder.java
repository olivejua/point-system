package dev.olivejua.pointsystem.common.infrastructure;

import dev.olivejua.pointsystem.common.service.DateTimeHolder;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

@Component
public class SystemDateTimeHolder implements DateTimeHolder {

    @Override
    public LocalDateTime now() {
        return LocalDateTime.now();
    }
}
