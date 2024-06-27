package dev.olivejua.pointsystem.domain;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.hibernate.annotations.Comment;

import java.time.LocalDateTime;

@RequiredArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
@Table(name = "products")
@Entity
public class ProductEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Comment("상품명")
    @Column(unique = true, nullable = false)
    private String name;

    @Comment("상품금액")
    @Column(nullable = false)
    private int price;

    @Column(nullable = false)
    private LocalDateTime createdAt;

    public static ProductEntity create(String name, int price) {
        ProductEntity productEntity = new ProductEntity();
        productEntity.name = name;
        productEntity.price = price;

        return productEntity;
    }
}
