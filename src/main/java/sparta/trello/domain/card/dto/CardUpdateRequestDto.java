package sparta.trello.domain.card.dto;

import lombok.Data;

import java.time.LocalDate;

@Data
public class CardUpdateRequestDto {
    String content;
    String nickname;
    LocalDate deadline;
}
