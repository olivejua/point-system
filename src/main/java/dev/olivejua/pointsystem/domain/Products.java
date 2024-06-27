package dev.olivejua.pointsystem.domain;

import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import static lombok.AccessLevel.PRIVATE;

@NoArgsConstructor(access = PRIVATE)
public class Products {
    private final List<ProductEntity> content = new ArrayList<>();

    public static Products create(List<ProductEntity> addedList) {
        Products products = new Products();
        products.addAll(addedList);
        return products;
    }

    private void addAll(List<ProductEntity> productEntities) {
        validate(productEntities);

        content.addAll(productEntities);

        removeNull();
    }

    private void validate(List<ProductEntity> productEntities) {
        if (productEntities ==null) {
            throw new NullPointerException();
        }
    }

    private void removeNull() {
        content.removeAll(Collections.singleton(null));
    }

    public int totalPrice() {
        return content.stream()
                .map(ProductEntity::getPrice)
                .reduce(0, Integer::sum);
    }
}
