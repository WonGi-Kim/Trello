package sparta.trello.domain.user.dto;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class ReissueTokenRequestDto {

    private String refresh;

}
