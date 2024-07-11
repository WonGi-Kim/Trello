package sparta.trello.domain.board;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import sparta.trello.domain.board.dto.BoardRequestDto;
import sparta.trello.domain.board.dto.BoardResponseDto;
import sparta.trello.domain.user.User;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class BoardService {

    private final BoardRepository boardRepository;
    private final InviteRepository inviteRepository;

    public BoardResponseDto addBoard(BoardRequestDto boardRequestDto, User user) {

        Board board =Board.builder().
                boardName(boardRequestDto.getBoardName()).
                introduction(boardRequestDto.getIntroduction()).
                user(user).
                build();
        Board saveBoard = boardRepository.save(board);
        return new BoardResponseDto(saveBoard);

    }

    public List<BoardResponseDto> getBoards(User user) {
        List<Board> boardList = new ArrayList<>();
        boardList.addAll(boardRepository.findByUser(user));
        boardList.addAll(inviteRepository.findByUser(user));

        return boardList.stream()
                .map(BoardResponseDto::new)
                .toList();
    }

}
