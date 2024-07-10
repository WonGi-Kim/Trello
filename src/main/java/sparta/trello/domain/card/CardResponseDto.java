package sparta.trello.domain.card;

import lombok.Data;
import sparta.trello.domain.status.Status;

import java.time.LocalDate;

@Data
public class CardResponseDto {

    String title;
    String statusTitle;
    String content;
    LocalDate deadline;

    public CardResponseDto(String content, String title, LocalDate deadline, Status status) {
        this.content = content;
        this.deadline = deadline;
        this.title = title;
        this.statusTitle = status.getTitle();
    }
}
