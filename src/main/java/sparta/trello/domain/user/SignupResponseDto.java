package sparta.trello.domain.user;

import lombok.Data;

@Data
public class SignupResponseDto {

    private String email;
    private String nickname;

    public SignupResponseDto(User user) {
        this.email = user.getEmail();
        this.nickname = user.getNickname();
    }

}
