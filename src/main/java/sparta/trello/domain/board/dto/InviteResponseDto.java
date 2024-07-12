package sparta.trello.domain.board.dto;

import lombok.Data;
import sparta.trello.domain.board.Invite;

@Data
public class InviteResponseDto {
    private String email;

    public InviteResponseDto(Invite invite) {

        this.email = invite.getUser().getEmail();

    }
}
