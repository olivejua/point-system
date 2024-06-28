package dev.olivejua.pointsystem.common.exception;

public class DuplicateAttributeException extends RuntimeException {

    public DuplicateAttributeException(String attributeName) {
        super(attributeName + "이(가) 이미 존재합니다.");
    }
}
