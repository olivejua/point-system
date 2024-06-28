package dev.olivejua.pointsystem.order.service;

public class BuyerDoesNotMatchException extends RuntimeException {

    public BuyerDoesNotMatchException() {
        super("요청자와 상품 구매자가 일치하지 않습니다.");
    }
}
