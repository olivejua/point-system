package dev.olivejua.pointsystem.common.exception;

public class InvalidAttributeFormatException extends RuntimeException {

    public InvalidAttributeFormatException(String attributeName) {
        super(attributeName + "이(가) 비어있거나 잘못된 형식입니다.");
    }
}
