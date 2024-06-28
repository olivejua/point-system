package dev.olivejua.pointsystem.user.domain;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.NullAndEmptySource;
import org.junit.jupiter.params.provider.ValueSource;

import static org.assertj.core.api.Assertions.assertThat;

public class UserCreateTest {

    @ParameterizedTest
    @NullAndEmptySource
    @ValueSource(strings = {"olivajua", "tmfrl4710@", "tmfrl4710@gmail", "tmfrl4710@gmail."})
    void 이메일이_빈값이거나_이메일_형식이_아니면_true를_반환한다(String value) {
        //given
        //when
        UserCreate userCreate = new UserCreate(value, "olivejua");
        boolean result = userCreate.hasInvalidEmail();

        //then
        assertThat(result).isTrue();
    }

    @Test
    void 이메일이_빈값이거나_이메일_형식이_아니면_false를_반환한다() {
        //given
        //when
        UserCreate userCreate = new UserCreate("tmfrl4710@gmail.com", "olivejua");
        boolean result = userCreate.hasInvalidEmail();

        //then
        assertThat(result).isFalse();
    }

    @Test
    void 닉네임이_유효한값이면_true를_반환한다() {
        //given
        //when
        UserCreate userCreate = new UserCreate("tmfrl4710@gmail.com", "olivejua");
        boolean result = userCreate.hasInvalidNickname();

        //then
        assertThat(result).isFalse();
    }

    @ParameterizedTest
    @NullAndEmptySource
    @ValueSource(strings = {"123olive", "!", "#abc#", "1", ","})
    void 닉네임이_비어있거나_첫글자가_영문_또는_한글로_시작하지_않으면_true를_반환한다(String value) {
        //given
        //when
        UserCreate userCreate = new UserCreate("tmfrl4710@gmail.com", value);
        boolean result = userCreate.hasInvalidNickname();

        //then
        assertThat(result).isTrue();
    }

}