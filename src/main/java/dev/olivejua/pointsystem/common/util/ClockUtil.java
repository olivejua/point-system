package dev.olivejua.pointsystem.common.util;

import java.time.*;

public class ClockUtil {

    public static long toMillis(LocalDateTime localDateTime) {
        ZonedDateTime zonedDateTime = localDateTime.atZone(ZoneOffset.UTC);
        Instant instant = zonedDateTime.toInstant();
        return instant.toEpochMilli();
    }

    public static LocalDateTime toLocalDateTime(long timestamp) {
        Instant instant = Instant.ofEpochMilli(timestamp);
        return LocalDateTime.ofInstant(instant, ZoneOffset.UTC);
    }

    public static LocalDate toLocalDate(long timestamp) {
        return toLocalDateTime(timestamp).toLocalDate();
    }
}
