package sparta.trello.domain.card;

import lombok.Data;

import java.time.LocalDate;

@Data
public class CardRequestDto {
    String title;
    String content;
    LocalDate deadline;
}
