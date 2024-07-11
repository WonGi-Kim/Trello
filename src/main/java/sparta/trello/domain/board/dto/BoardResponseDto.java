package sparta.trello.domain.board.dto;

import lombok.Data;
import sparta.trello.domain.board.Board;

@Data
public class BoardResponseDto {

    private Long id;
    private String boardName;
    private String introduction;

    public BoardResponseDto(Board board){

        id = board.getId();
        boardName = board.getBoardName();
        introduction = board.getIntroduction();

    }
}
