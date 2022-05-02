package dev.olivejua.pointsystem.domain;

import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import static lombok.AccessLevel.PRIVATE;

@NoArgsConstructor(access = PRIVATE)
public class Products {
    private final List<Product> content = new ArrayList<>();

    public static Products create(List<Product> addedList) {
        Products products = new Products();
        products.addAll(addedList);
        return products;
    }

    private void addAll(List<Product> products) {
        validate(products);

        content.addAll(products);

        removeNull();
    }

    private void validate(List<Product> products) {
        if (products==null) {
            throw new NullPointerException();
        }
    }

    private void removeNull() {
        content.removeAll(Collections.singleton(null));
    }

    public int totalPrice() {
        return content.stream()
                .map(Product::getPrice)
                .reduce(0, Integer::sum);
    }
}
