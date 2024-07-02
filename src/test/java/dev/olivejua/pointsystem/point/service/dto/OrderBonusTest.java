package dev.olivejua.pointsystem.point.service.dto;

import dev.olivejua.pointsystem.common.util.ClockUtil;
import dev.olivejua.pointsystem.order.domain.Order;
import dev.olivejua.pointsystem.order.domain.OrderStatus;
import dev.olivejua.pointsystem.point.domain.accrualbonus.OrderBonus;
import dev.olivejua.pointsystem.product.domain.Product;
import dev.olivejua.pointsystem.user.domain.User;
import dev.olivejua.pointsystem.user.domain.UserStatus;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;

import static org.assertj.core.api.Assertions.assertThat;

class OrderBonusTest {

    @Test
    void 적립대상여부를_검증할때_무조건_true를_반환한다() {
        //given
        long createdDate = ClockUtil.toMillis(LocalDate.of(2024, 6, 1).atStartOfDay());
        Order order = Order.builder()
                .id(1L)
                .buyer(User.builder()
                        .id(1L)
                        .email("tmfrl4710@gmail.com")
                        .nickname("olivejua")
                        .status(UserStatus.ACTIVE)
                        .createdAt(createdDate)
                        .modifiedAt(createdDate)
                        .build())
                .product(Product.builder()
                        .id(1L)
                        .name("자바의 신")
                        .price(20_000)
                        .createdAt(LocalDate.of(2024, 6, 1).atStartOfDay())
                        .build())
                .amount(20_000)
                .createdAt(LocalDate.of(2024, 6, 1).atStartOfDay())
                .modifiedAt(LocalDate.of(2024, 6, 1).atStartOfDay())
                .status(OrderStatus.ORDERED)
                .build();

        OrderBonus orderBonus = new OrderBonus(order);

        //when
        boolean result = orderBonus.isEligibleUserForPoints(null);

        //then
        assertThat(result).isTrue();
    }

    @Test
    void 주문금액에_5퍼센트를_적립금으로_반환한다() {
        //given
        long createdDate = ClockUtil.toMillis(LocalDate.of(2024, 6, 1).atStartOfDay());
        Order order = Order.builder()
                .id(1L)
                .buyer(User.builder()
                        .id(1L)
                        .email("tmfrl4710@gmail.com")
                        .nickname("olivejua")
                        .status(UserStatus.ACTIVE)
                        .createdAt(createdDate)
                        .modifiedAt(createdDate)
                        .build())
                .product(Product.builder()
                        .id(1L)
                        .name("자바의 신")
                        .price(20_000)
                        .createdAt(LocalDate.of(2024, 6, 1).atStartOfDay())
                        .build())
                .amount(20_000)
                .createdAt(LocalDate.of(2024, 6, 1).atStartOfDay())
                .modifiedAt(LocalDate.of(2024, 6, 1).atStartOfDay())
                .status(OrderStatus.ORDERED)
                .build();

        OrderBonus orderBonus = new OrderBonus(order);

        //when
        long amount = orderBonus.getAmount();

        //then
        assertThat(amount).isEqualTo(1_000);
    }
}