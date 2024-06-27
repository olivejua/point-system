package dev.olivejua.pointsystem.domain;

import jakarta.persistence.*;
import lombok.Getter;
import org.hibernate.annotations.Comment;

@Getter
@Entity
public class Product extends BaseTimeEntity {

    @Column(name = "PRODUCT_ID")
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Comment("상품명")
    @Column(unique = true, nullable = false)
    private String name;

    @Comment("상품금액")
    @Column(nullable = false)
    private int price;

    public static Product create(String name, int price) {
        Product product = new Product();
        product.name = name;
        product.price = price;

        return product;
    }
}
