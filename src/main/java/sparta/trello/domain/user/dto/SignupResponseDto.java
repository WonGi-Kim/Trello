package sparta.trello.domain.user.dto;

import lombok.Data;
import sparta.trello.domain.user.User;

@Data
public class SignupResponseDto {

    private String email;
    private String nickname;

    public SignupResponseDto(User user) {
        this.email = user.getEmail();
        this.nickname = user.getNickname();
    }

}
