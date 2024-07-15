package sparta.trello.domain.card.dto;

import lombok.Data;
import sparta.trello.domain.board.Board;
import sparta.trello.domain.status.Status;
import sparta.trello.domain.user.User;

import java.time.LocalDate;

@Data
public class FindCardResponseDto {
    String title;
    String boardTitle;
    String boardIntro;
    String statusTitle;
    String content;
    String nickname;
    LocalDate deadline;

    public FindCardResponseDto(String title, Board board, Status status, String content, User user, LocalDate deadline){
        this.title = title;
        this.boardTitle = board.getBoardName();
        this.boardIntro = board.getIntroduction();
        this.statusTitle = status.getTitle();
        this.content = content;
        this.nickname = user.getNickname();
        this.deadline = deadline;
    }
}
