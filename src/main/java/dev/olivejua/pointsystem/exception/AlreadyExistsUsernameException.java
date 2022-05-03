package dev.olivejua.pointsystem.exception;

public class AlreadyExistsUsernameException extends RuntimeException {

    public AlreadyExistsUsernameException(String username) {
        super("이미 존재하는 유저명입니다. username="+username);
    }
}
