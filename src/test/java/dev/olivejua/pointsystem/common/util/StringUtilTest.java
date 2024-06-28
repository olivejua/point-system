package dev.olivejua.pointsystem.common.util;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.NullAndEmptySource;
import org.junit.jupiter.params.provider.ValueSource;

import static org.assertj.core.api.Assertions.assertThat;

public class StringUtilTest {

    @ParameterizedTest
    @NullAndEmptySource
    @ValueSource(strings = {"  ", "\t", "\n"})
    void 파라미터가_빈값이면_true를_반환한다(String value) {
        //given
        //when
        boolean result = StringUtil.isNullOrBlank(value);

        //then
        assertThat(result).isTrue();
    }

    @ParameterizedTest
    @ValueSource(strings = {"a", "1234", "가나"})
    void 파라미터가_빈값이_아니라면_false를_반환한다(String value) {
        //given
        //when
        boolean result = StringUtil.isNullOrBlank(value);

        //then
        assertThat(result).isFalse();
    }

    @ParameterizedTest
    @ValueSource(chars = {'a', 'b', 'z'})
    void 문자가_알파벳이라면_true를_반환한다(char value) {
        //given
        //when
        boolean result = StringUtil.isAlphabet(value);

        //then
        assertThat(result).isTrue();
    }

    @ParameterizedTest
    @ValueSource(chars = {'가', '1', ',', '!'})
    void 문자가_알파벳이_아니라면_false를_반환한다(char value) {
        //given
        //when
        boolean result = StringUtil.isAlphabet(value);

        //then
        assertThat(result).isFalse();
    }

    @ParameterizedTest
    @ValueSource(chars = {'가', '마', '하'})
    void 문자가_한글이라면_true를_반환한다(char value) {
        //given
        //when
        boolean result = StringUtil.isKorean(value);

        //then
        assertThat(result).isTrue();
    }

    @ParameterizedTest
    @ValueSource(chars = {'a', '1', ',', '!'})
    void 문자가_한글이_아니라면_false를_반환한다(char value) {
        //given
        //when
        boolean result = StringUtil.isKorean(value);

        //then
        assertThat(result).isFalse();
    }

    @Test
    void 문자열이_이메일형식이라면_true를_반환한다() {
        //given
        //when
        boolean result = StringUtil.isEmail("tmfrl4710@gmail.com");

        //then
        assertThat(result).isTrue();
    }

    @ParameterizedTest
    @ValueSource(strings = {"", "  ", "olivajua", "tmfrl4710@", "tmfrl4710@gmail", "tmfrl4710@gmail."})
    void 문자가_이메일형식이_아니라면_false를_반환한다(String value) {
        //given
        //when
        boolean result = StringUtil.isEmail(value);

        //then
        assertThat(result).isFalse();
    }

}