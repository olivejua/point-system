package dev.olivejua.pointsystem.common.util;

import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;

import static org.assertj.core.api.Assertions.assertThat;

class ClockUtilTest {

    @Test
    void LocalDateTime을_밀리세컨드로_변환_후_다시_LocalDateTime으로_변환하면_같은값이_나온다() {
        //given
        LocalDateTime localDateTime = LocalDateTime.of(2024, 6, 1, 0, 0, 0);
        long millis = ClockUtil.toMillis(localDateTime);

        //when
        LocalDateTime result = ClockUtil.toLocalDateTime(millis);

        //then
        assertThat(result).isEqualTo(localDateTime);
    }

    @Test
    void 밀리세컨드를_LocalDateTime으로_변환_후_다시_밀리세컨드로_변환하면_같은값이_나온다() {
        //given
        long millis = 1717167600000L;
        LocalDateTime localDateTime = ClockUtil.toLocalDateTime(millis);

        //when
        long result = ClockUtil.toMillis(localDateTime);

        //then
        assertThat(result).isEqualTo(millis);
    }
}