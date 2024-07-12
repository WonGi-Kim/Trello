package sparta.trello.domain.board.dto;

import jakarta.validation.constraints.Email;
import lombok.Data;

@Data
public class InviteRequestDto {
    @Email(message = "초대하고 싶은 유저의 이메일을 입력해주세요.")
    private String email;
}
