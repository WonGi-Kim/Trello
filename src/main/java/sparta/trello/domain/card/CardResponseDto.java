package sparta.trello.domain.card;

import lombok.Data;
import sparta.trello.domain.status.Status;
import sparta.trello.domain.user.User;

import java.time.LocalDate;

@Data
public class CardResponseDto {

    String title;
    String statusTitle;
    String content;
    String nickname;
    LocalDate deadline;

    public CardResponseDto(String content, String title, LocalDate deadline, Status status, User user) {
        this.content = content;
        this.deadline = deadline;
        this.title = title;
        this.statusTitle = status.getTitle();
        this.nickname = user.getNickname();
    }
}
