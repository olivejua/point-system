package dev.olivejua.pointsystem.product.service;

import dev.olivejua.pointsystem.common.exception.NotFoundResourceException;
import dev.olivejua.pointsystem.mock.FakeProductRepository;
import dev.olivejua.pointsystem.product.domain.Product;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

public class ProductServiceTest {
    private ProductService productService;

    @BeforeEach
    void setUp() {
        FakeProductRepository productRepository = new FakeProductRepository();
        productService = ProductService.builder()
                .productRepository(productRepository)
                .build();

        productRepository.save(Product.builder()
                        .id(1L)
                        .name("세이노의 가르침")
                        .price(10_000)
                        .createdAt(LocalDate.of(2024, 6, 1).atStartOfDay())
                        .build());
    }

    @Test
    void getById로_상품정보를_조회할_수_있다() {
        //given
        //when
        Product product = productService.getById(1L);

        //then
        assertThat(product.getId()).isEqualTo(1L);
        assertThat(product.getName()).isEqualTo("세이노의 가르침");
        assertThat(product.getPrice()).isEqualTo(10_000);
    }

    @Test
    void getById로_상품정보가_없다면_예외를_던진다() {
        //given
        //when
        //then
        assertThatThrownBy(() -> productService.getById(2L))
                .isInstanceOf(NotFoundResourceException.class);

    }
}