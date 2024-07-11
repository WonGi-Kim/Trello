package sparta.trello.domain.board.dto;

import lombok.Data;
import sparta.trello.domain.board.Board;

@Data
public class BoardResponseDto {

    private String boardName;
    private String introduction;

    public BoardResponseDto(Board board){

        boardName = board.getBoardName();
        introduction = board.getIntroduction();

    }
}
