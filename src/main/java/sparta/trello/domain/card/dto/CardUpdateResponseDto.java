package sparta.trello.domain.card.dto;

import lombok.Data;

import java.time.LocalDate;
@Data
public class CardUpdateResponseDto {
    String content;
    String nickname;
    LocalDate deadline;

    public CardUpdateResponseDto(String content, String nickname, LocalDate deadline) {
        this.content = content;
        this.nickname = nickname;
        this.deadline = deadline;
    }
}
