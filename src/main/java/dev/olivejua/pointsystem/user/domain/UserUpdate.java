package dev.olivejua.pointsystem.user.domain;

import dev.olivejua.pointsystem.common.util.StringUtil;
import lombok.Getter;

@Getter
public class UserUpdate {
    private final String email;
    private final String nickname;

    public UserUpdate(String email, String nickname) {
        this.email = email;
        this.nickname = nickname;
    }

    public boolean hasSameEmailAndNicknameFrom(User user) {
        return user.getEmail().equals(email) &&
                user.getNickname().equals(nickname);
    }

    public boolean hasInvalidEmail() {
        return !StringUtil.isEmail(email);
    }

    public boolean hasInvalidNickname() {
        if (StringUtil.isNullOrBlank(nickname)) {
            return true;
        }

        char firstChar = nickname.charAt(0);
        return !StringUtil.isAlphabet(firstChar) && !StringUtil.isKorean(firstChar);
    }
}
