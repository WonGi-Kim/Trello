package sparta.trello.domain.user.dto;

import lombok.Data;

@Data
public class TokenDto {

    private String access;
    private String refresh;

    public TokenDto(String access, String refresh) {

        this.access = access;
        this.refresh = refresh;

    }

}
