package dev.olivejua.pointsystem.order.controller.request;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class OrderCreateRequestTest {

    @Test
    void 포인트_금액은_음수면_객체를_생성시_예외가_발생한다() {
        //given
        //when
        //then
        Assertions.assertThatThrownBy(() -> new OrderCreateRequest(1L, -10))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("Points cannot be negative");
    }

    @Test
    void ProductID가_null이면_객체를_생성시_예외가_발생한다() {
        //given
        //when
        //then
        Assertions.assertThatThrownBy(() -> new OrderCreateRequest(null, 0))
                .isInstanceOf(NullPointerException.class)
                .hasMessage("Product ID must not be null");
    }
}