package dev.olivejua.pointsystem.order.domain;

import dev.olivejua.pointsystem.mock.TestDateTimeHolder;
import dev.olivejua.pointsystem.product.domain.Product;
import dev.olivejua.pointsystem.user.domain.User;
import dev.olivejua.pointsystem.user.domain.UserStatus;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.time.LocalDateTime;

import static org.assertj.core.api.Assertions.assertThat;

public class OrderTest {

    @Test
    void Product과_User로_객체를_생성할_수_있다() {
        //given
        Product product = Product.builder()
                .id(1L)
                .name("세이노의 가르침")
                .price(10_000)
                .createdAt(LocalDate.of(2024, 6, 1).atStartOfDay())
                .build();
        User buyer = User.builder()
                .id(1L)
                .email("tmfrl4710@gmail.com")
                .nickname("olivejua")
                .status(UserStatus.ACTIVE)
                .createdAt(LocalDate.of(2024, 6, 1).atStartOfDay())
                .modifiedAt(LocalDate.of(2024, 6, 1).atStartOfDay())
                .build();

        //when
        LocalDateTime now = LocalDateTime.now();
        Order order = Order.of(product, buyer, new TestDateTimeHolder(now));

        //then
        assertThat(order.getBuyer()).isEqualTo(buyer);
        assertThat(order.getProduct()).isEqualTo(product);
        assertThat(order.getAmount()).isEqualTo(10_000);
        assertThat(order.getCreatedAt()).isEqualTo(now);
    }


}