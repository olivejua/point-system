package dev.olivejua.pointsystem.common.exception;

public class NotFoundResourceException extends RuntimeException {

    public NotFoundResourceException(String resourceName) {
        super(resourceName + "을(를) 찾을 수 없습니다.");
    }
}
