package sparta.trello.domain.user.dto;

import lombok.Data;

@Data
public class LoginResponseDto {

    private String access;
    private String refresh;

    public LoginResponseDto(String access, String refresh) {
        this.access = access;
        this.refresh = refresh;
    }

}
