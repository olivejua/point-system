package dev.olivejua.pointsystem.common.util;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;

public class ClockUtil {

    public static long millisFrom(LocalDateTime localDateTime) {
        ZonedDateTime zonedDateTime = localDateTime.atZone(ZoneId.systemDefault());
        Instant instant = zonedDateTime.toInstant();
        return instant.toEpochMilli();
    }

}
