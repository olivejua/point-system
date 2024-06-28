package dev.olivejua.pointsystem.common.exception;

public class ReviewAlreadyExistsException extends RuntimeException {

    public ReviewAlreadyExistsException() {
        super("이미 작성된 리뷰가 존재합니다.");
    }
}
