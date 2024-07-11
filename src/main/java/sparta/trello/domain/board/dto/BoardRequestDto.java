package sparta.trello.domain.board.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class BoardRequestDto {

    @NotBlank(message = "보드이름을 작성해주세요.")
    private String boardName;

    @NotBlank(message = "한줄소개글을 작성해주세요.")
    private String introduction;
}
