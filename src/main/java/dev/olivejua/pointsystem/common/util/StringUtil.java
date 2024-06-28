package dev.olivejua.pointsystem.common.util;

public abstract class StringUtil {

    public static boolean isNullOrBlank(String value) {
        return value == null || value.trim().isEmpty();
    }

    public static boolean isAlphabet(char c) {
        return (c >= 'a' && c <= 'z') || (c >= 'A' && c <= 'Z');
    }

    public static boolean isKorean(char c) {
        return (c >= '가' && c <= '힣');
    }

    public static boolean isEmail(String value) {
        return !isNullOrBlank(value) && value.matches("^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,}$");
    }
}
