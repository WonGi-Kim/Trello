package sparta.trello.domain.card.dto;

import lombok.Data;
import sparta.trello.domain.user.User;

import java.time.LocalDate;
@Data
public class CardUpdateResponseDto {
    String title;
    String content;
    String userEmail;
    LocalDate deadline;

    public CardUpdateResponseDto(String title, String content, User user, LocalDate deadline) {
        this.title = title;
        this.content = content;
        this.userEmail = user.getEmail();
        this.deadline = deadline;
    }
}
