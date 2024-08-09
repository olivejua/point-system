package dev.olivejua.pointsystem.review.controller;

import dev.olivejua.pointsystem.common.util.ClockUtil;
import dev.olivejua.pointsystem.mock.TestClockHolder;
import dev.olivejua.pointsystem.mock.TestContainer;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;

public class ReviewControllerTest {

    @Test
    void 사용자는_상품리뷰를_작성할_수_있다() {
        //given
        TestContainer testContainer = TestContainer.builder()
                .clockHolder(new TestClockHolder(ClockUtil.toMillis(LocalDateTime.now())))
                .build();

        //when

        //then

    }
}
