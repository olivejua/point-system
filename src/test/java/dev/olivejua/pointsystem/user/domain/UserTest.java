package dev.olivejua.pointsystem.user.domain;

import dev.olivejua.pointsystem.common.exception.InvalidAttributeFormatException;
import dev.olivejua.pointsystem.common.util.ClockUtil;
import dev.olivejua.pointsystem.mock.TestClockHolder;
import org.junit.jupiter.api.Test;

import java.time.Clock;
import java.time.LocalDate;
import java.time.LocalDateTime;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

public class UserTest {

    @Test
    void userCreate를_이용하여_객체를_생성할_수_있다() {
        //given
        UserCreate userCreate = new UserCreate("tmfrl4710@gmail.com", "olivejua");

        //when
        long now = Clock.systemUTC().millis();
        User user = User.from(userCreate, new TestClockHolder(now));

        //then
        assertThat(user.getEmail()).isEqualTo("tmfrl4710@gmail.com");
        assertThat(user.getNickname()).isEqualTo("olivejua");
        assertThat(user.getStatus()).isEqualTo(UserStatus.ACTIVE);
        assertThat(user.getCreatedAt()).isEqualTo(now);
        assertThat(user.getModifiedAt()).isEqualTo(now);
    }

    @Test
    void 유저의_가입닉네임이_비어있거나_글자가_포함되지_않았다면_예외를_던진다() {
        //given
        UserCreate userCreate = new UserCreate("tmfrl4710@gmail.com", "123");

        //when
        //then
        assertThatThrownBy(() -> User.from(userCreate, new TestClockHolder(Clock.systemUTC().millis())))
                .isInstanceOf(InvalidAttributeFormatException.class)
                .hasMessage("유저의 이메일 또는 닉네임이(가) 비어있거나 잘못된 형식입니다.");
    }

    @Test
    void 가입이메일이_빈값이거나_잘못된_이메일형식이라면_예외를_던진다() {
        //given
        UserCreate userCreate = new UserCreate("tmfrl4710", "seulki");

        //when
        //then
        assertThatThrownBy(() -> User.from(userCreate, new TestClockHolder(Clock.systemUTC().millis())))
                .isInstanceOf(InvalidAttributeFormatException.class)
                .hasMessage("유저의 이메일 또는 닉네임이(가) 비어있거나 잘못된 형식입니다.");
    }

    @Test
    void 로그인하면_마지막로그인일시가_업데이트된다() {
        //given
        long createdAt = ClockUtil.toMillis(LocalDate.of(2024, 6, 1).atStartOfDay());
        TestClockHolder clockHolderWithCreatedDate = new TestClockHolder(createdAt);

        User user = User.builder()
                .id(1L)
                .email("tmfrl4710@gmail.com")
                .nickname("olivejua")
                .status(UserStatus.ACTIVE)
                .createdAt(clockHolderWithCreatedDate.millis())
                .modifiedAt(clockHolderWithCreatedDate.millis())
                .build();

        //when
        TestClockHolder clockHolderWithNow = new TestClockHolder(ClockUtil.toMillis(LocalDateTime.now()));
        user = user.login(clockHolderWithNow);

        //then
        assertThat(user.getLastLoginAt()).isEqualTo(clockHolderWithNow.millis());
    }

    @Test
    void UserUpdate로_유저정보를_업데이트할_수_있다() {
        //given
        long createdAt = ClockUtil.toMillis(LocalDate.of(2024, 6, 1).atStartOfDay());
        User user = User.builder()
                .id(1L)
                .email("tmfrl4710@gmail.com")
                .nickname("olivejua")
                .status(UserStatus.ACTIVE)
                .createdAt(createdAt)
                .modifiedAt(createdAt)
                .build();

        UserUpdate userUpdate = new UserUpdate("tmfrl4710@naver.com", "seulki");

        //when
        long now = ClockUtil.toMillis(LocalDateTime.now());
        user = user.update(userUpdate, new TestClockHolder(now));

        //then
        assertThat(user.getEmail()).isEqualTo(userUpdate.getEmail());
        assertThat(user.getNickname()).isEqualTo(userUpdate.getNickname());
        assertThat(user.getModifiedAt()).isEqualTo(now);
    }

    @Test
    void 닉네임과_이메일_값이_같다면_업데이트하지_않는다() {
        //given
        User user = User.builder()
                .id(1L)
                .email("tmfrl4710@gmail.com")
                .nickname("olivejua")
                .status(UserStatus.ACTIVE)
                .createdAt(ClockUtil.toMillis(LocalDate.of(2024, 6, 1).atStartOfDay()))
                .modifiedAt(ClockUtil.toMillis(LocalDate.of(2024, 6, 1).atStartOfDay()))
                .build();

        UserUpdate userUpdate = new UserUpdate("tmfrl4710@gmail.com", "olivejua");

        //when
        user = user.update(userUpdate, new TestClockHolder(ClockUtil.toMillis(LocalDateTime.now())));

        //then
        assertThat(user.getModifiedAt()).isEqualTo(ClockUtil.toMillis(LocalDate.of(2024, 6, 1).atStartOfDay()));
    }

    @Test
    void 유저의_변경닉네임이_비어있거나_글자가_포함되지_않았다면_예외를_던진다() {
        //given
        User user = User.builder()
                .id(1L)
                .email("tmfrl4710@gmail.com")
                .nickname("olivejua")
                .status(UserStatus.ACTIVE)
                .createdAt(ClockUtil.toMillis(LocalDate.of(2024, 6, 1).atStartOfDay()))
                .modifiedAt(ClockUtil.toMillis(LocalDate.of(2024, 6, 1).atStartOfDay()))
                .build();

        UserUpdate userUpdate = new UserUpdate("tmfrl4710@gmail.com", "123");

        //when
        //then
        assertThatThrownBy(() -> user.update(userUpdate, new TestClockHolder(ClockUtil.toMillis(LocalDateTime.now()))))
                .isInstanceOf(InvalidAttributeFormatException.class)
                .hasMessage("유저의 이메일 또는 닉네임이(가) 비어있거나 잘못된 형식입니다.");
    }

    @Test
    void 변경이메일이_빈값이거나_잘못된_이메일형식이라면_예외를_던진다() {
        //given
        User user = User.builder()
                .id(1L)
                .email("tmfrl4710@gmail.com")
                .nickname("olivejua")
                .status(UserStatus.ACTIVE)
                .createdAt(ClockUtil.toMillis(LocalDate.of(2024, 6, 1).atStartOfDay()))
                .modifiedAt(ClockUtil.toMillis(LocalDate.of(2024, 6, 1).atStartOfDay()))
                .build();

        UserUpdate userUpdate = new UserUpdate("tmfrl4710", "olivejua");

        //when
        //then
        assertThatThrownBy(() -> user.update(userUpdate, new TestClockHolder(ClockUtil.toMillis(LocalDateTime.now()))))
                .isInstanceOf(InvalidAttributeFormatException.class)
                .hasMessage("유저의 이메일 또는 닉네임이(가) 비어있거나 잘못된 형식입니다.");
    }

    @Test
    void isSameAs로_동일유저라면_true를_반환한다() {
        //given
        User user1 = User.builder()
                .id(1L)
                .email("tmfrl4710@gmail.com")
                .nickname("olivejua")
                .status(UserStatus.ACTIVE)
                .createdAt(ClockUtil.toMillis(LocalDate.of(2024, 6, 1).atStartOfDay()))
                .modifiedAt(ClockUtil.toMillis(LocalDate.of(2024, 6, 1).atStartOfDay()))
                .build();

        User user2 = User.builder()
                .id(1L)
                .email("tmfrl4710@gmail.com")
                .nickname("olivejua")
                .status(UserStatus.ACTIVE)
                .createdAt(ClockUtil.toMillis(LocalDate.of(2024, 6, 1).atStartOfDay()))
                .modifiedAt(ClockUtil.toMillis(LocalDate.of(2024, 6, 1).atStartOfDay()))
                .build();

        //when
        //then
        assertThat(user1.isSameAs(user2)).isTrue();
    }

    @Test
    void isSameAs로_동일유저가_아니라면_false를_반환한다() {
        //given
        User user1 = User.builder()
                .id(1L)
                .email("tmfrl4710@gmail.com")
                .nickname("olivejua")
                .status(UserStatus.ACTIVE)
                .createdAt(ClockUtil.toMillis(LocalDate.of(2024, 6, 1).atStartOfDay()))
                .modifiedAt(ClockUtil.toMillis(LocalDate.of(2024, 6, 1).atStartOfDay()))
                .build();

        User user2 = User.builder()
                .id(2L)
                .email("tmfrl4710@naver.com")
                .nickname("seulki")
                .status(UserStatus.ACTIVE)
                .createdAt(ClockUtil.toMillis(LocalDate.of(2024, 6, 1).atStartOfDay()))
                .modifiedAt(ClockUtil.toMillis(LocalDate.of(2024, 6, 1).atStartOfDay()))
                .build();

        //when
        //then
        assertThat(user1.isSameAs(user2)).isFalse();
    }
}