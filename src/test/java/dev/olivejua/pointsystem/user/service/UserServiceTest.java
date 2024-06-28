package dev.olivejua.pointsystem.user.service;

import dev.olivejua.pointsystem.common.exception.DuplicateAttributeException;
import dev.olivejua.pointsystem.common.exception.NotFoundResourceException;
import dev.olivejua.pointsystem.mock.FakeUserRepository;
import dev.olivejua.pointsystem.mock.TestDateTimeHolder;
import dev.olivejua.pointsystem.user.domain.User;
import dev.olivejua.pointsystem.user.domain.UserCreate;
import dev.olivejua.pointsystem.user.domain.UserStatus;
import dev.olivejua.pointsystem.user.domain.UserUpdate;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.time.LocalDateTime;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

public class UserServiceTest {
    private UserService userService;
    private FakeUserRepository fakeUserRepository;

    private LocalDateTime now = LocalDateTime.now();

    @BeforeEach
    void setUp() {
        fakeUserRepository = FakeUserRepository.builder()
                .build();
        userService = UserService.builder()
                .userRepository(fakeUserRepository)
                .dateTimeHolder(new TestDateTimeHolder(now))
                .build();
    }

    @Test
    void userCreate를_이용하여_유저를_저장할_수_있다() {
        //given
        UserCreate userCreate = new UserCreate("tmfrl4710@gmail.com", "olivejua");

        //when
        User user = userService.join(userCreate);

        //then
        assertThat(user).isNotNull();
        assertThat(user.getId()).isEqualTo(1);
        assertThat(user.getEmail()).isEqualTo(userCreate.getEmail());
        assertThat(user.getNickname()).isEqualTo(userCreate.getNickname());
        assertThat(user.getStatus()).isEqualTo(UserStatus.ACTIVE);
        assertThat(user.getCreatedAt()).isEqualTo(now);
        assertThat(user.getModifiedAt()).isEqualTo(now);
        assertThat(user.getLastLoginAt()).isNull();
    }

    @Test
    void 가입이메일이_이미_존재한다면_예외를_던진다() {
        //given
        fakeUserRepository.save(User.builder()
                .id(1L)
                .email("tmfrl4710@gmail.com")
                .nickname("olivejua")
                .status(UserStatus.ACTIVE)
                .createdAt(LocalDate.of(2024, 6, 1).atStartOfDay())
                .modifiedAt(LocalDate.of(2024, 6, 1).atStartOfDay())
                .build());

        UserCreate userCreate = new UserCreate("tmfrl4710@gmail.com", "seulki");

        //when
        //then
        assertThatThrownBy(() -> userService.join(userCreate))
                .isInstanceOf(DuplicateAttributeException.class)
                .hasMessage("유저의 이메일이(가) 이미 존재합니다.");
    }

    @Test
    void id로_유저정보를_조회할_수_있다() {
        //given
        User user = fakeUserRepository.save(User.builder()
                .id(1L)
                .email("tmfrl4710@gmail.com")
                .nickname("olivejua")
                .status(UserStatus.ACTIVE)
                .createdAt(LocalDate.of(2024, 6, 1).atStartOfDay())
                .modifiedAt(LocalDate.of(2024, 6, 1).atStartOfDay())
                .build());

        //when
        User result = userService.getMyInfo(1);

        //then
        assertThat(result).isNotNull();
        assertThat(result.getId()).isEqualTo(user.getId());
        assertThat(result.getEmail()).isEqualTo(user.getEmail());
        assertThat(result.getNickname()).isEqualTo(user.getNickname());
        assertThat(result.getStatus()).isEqualTo(user.getStatus());
        assertThat(result.getCreatedAt()).isEqualTo(user.getCreatedAt());
        assertThat(result.getModifiedAt()).isEqualTo(user.getModifiedAt());
        assertThat(result.getLastLoginAt()).isEqualTo(user.getLastLoginAt());
    }

    @Test
    void id로_유저정보가_존재하지_않는다면_에러를_던진다() {
        //given
        User user = fakeUserRepository.save(User.builder()
                .id(1L)
                .email("tmfrl4710@gmail.com")
                .nickname("olivejua")
                .status(UserStatus.ACTIVE)
                .createdAt(LocalDate.of(2024, 6, 1).atStartOfDay())
                .modifiedAt(LocalDate.of(2024, 6, 1).atStartOfDay())
                .build());

        //when
        //then
        assertThatThrownBy(() -> userService.getMyInfo(2))
                .isInstanceOf(NotFoundResourceException.class)
                .hasMessage("User을(를) 찾을 수 없습니다.");
    }

    @Test
    void 로그인할_때_이메일이_존재하지_않는다면_에러를_던진다() {
        //given
        fakeUserRepository.save(User.builder()
                .id(1L)
                .email("tmfrl4710@gmail.com")
                .nickname("olivejua")
                .status(UserStatus.ACTIVE)
                .createdAt(LocalDate.of(2024, 6, 1).atStartOfDay())
                .modifiedAt(LocalDate.of(2024, 6, 1).atStartOfDay())
                .build());

        //when
        //then
        assertThatThrownBy(() -> userService.login("tmfrl4710@naver.com"))
                .isInstanceOf(NotFoundResourceException.class)
                .hasMessage("User을(를) 찾을 수 없습니다.");
    }

    @Test
    void 유저를_로그인시키면_마지막로그인일시가_업데이트된다() {
        //given
        fakeUserRepository.save(User.builder()
                .id(1L)
                .email("tmfrl4710@gmail.com")
                .nickname("olivejua")
                .status(UserStatus.ACTIVE)
                .createdAt(LocalDate.of(2024, 6, 1).atStartOfDay())
                .modifiedAt(LocalDate.of(2024, 6, 1).atStartOfDay())
                .build());

        //when
        userService.login("tmfrl4710@gmail.com");

        //then
        User result = fakeUserRepository.getById(1);
        assertThat(result.getLastLoginAt()).isEqualTo(now);
    }

    @Test
    void 유저_로그인시_출석체크_포인트적립_대상이라면_적립한다() {
        //given

        //when

        //then
    }

    @Test
    void UserUpdate로_유저정보를_변경할_수_있다() {
        //given
        User user = fakeUserRepository.save(User.builder()
                .id(1L)
                .email("tmfrl4710@gmail.com")
                .nickname("olivejua")
                .status(UserStatus.ACTIVE)
                .createdAt(LocalDate.of(2024, 6, 1).atStartOfDay())
                .modifiedAt(LocalDate.of(2024, 6, 1).atStartOfDay())
                .build());

        UserUpdate userUpdate = new UserUpdate("tmfrl4710@naver.com", "seulki");

        //when
        User result = userService.update(1, userUpdate);

        //then
        assertThat(result.getId()).isEqualTo(user.getId());
        assertThat(result.getEmail()).isEqualTo(userUpdate.getEmail());
        assertThat(result.getNickname()).isEqualTo(userUpdate.getNickname());
        assertThat(result.getStatus()).isEqualTo(user.getStatus());
        assertThat(result.getCreatedAt()).isEqualTo(user.getCreatedAt());
        assertThat(result.getModifiedAt()).isEqualTo(now);
        assertThat(result.getLastLoginAt()).isEqualTo(user.getLastLoginAt());
    }
}