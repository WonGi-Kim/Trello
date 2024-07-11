package sparta.trello.domain.board;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import sparta.trello.domain.board.dto.BoardRequestDto;
import sparta.trello.domain.board.dto.BoardResponseDto;
import sparta.trello.domain.user.User;

@Service
@RequiredArgsConstructor
public class BoardService {

    private final BoardRepository boardRepository;

    public BoardResponseDto addBoard(BoardRequestDto boardRequestDto, User user) {

        Board board =Board.builder().
                boardName(boardRequestDto.getBoardName()).
                introduction(boardRequestDto.getIntroduction()).
                user(user).
                build();
        Board saveBoard = boardRepository.save(board);
        return new BoardResponseDto(saveBoard);

    }

}
