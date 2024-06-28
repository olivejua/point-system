package dev.olivejua.pointsystem.order.domain;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class OrderCreateTest {

    @Test
    void 포인트사용금액이_0보다_크면_true를_반환한다() {
        //given
        //when
        OrderCreate orderCreate = new OrderCreate(1, 1, 1000);
        boolean result = orderCreate.hasPointsRedeemed();

        //then
        assertThat(result).isTrue();
    }

    @Test
    void 포인트사용금액이_0보다_크지않으면_false를_반환한다() {
        //given
        //when
        OrderCreate orderCreate = new OrderCreate(1, 1, 0);
        boolean result = orderCreate.hasPointsRedeemed();

        //then
        assertThat(result).isFalse();
    }
}