package sparta.trello.domain.board;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import sparta.trello.domain.board.dto.BoardRequestDto;
import sparta.trello.domain.board.dto.BoardResponseDto;
import sparta.trello.domain.board.dto.InviteRequestDto;
import sparta.trello.domain.board.dto.InviteResponseDto;
import sparta.trello.domain.user.User;
import sparta.trello.domain.user.UserRepository;
import sparta.trello.global.exception.CustomException;
import sparta.trello.global.exception.ErrorCode;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class BoardService {

    private final BoardRepository boardRepository;
    private final InviteRepository inviteRepository;
    private final UserRepository userRepository;

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

        if(user.getRole().equals(User.Role.MANAGER)){
            boardList = boardRepository.findAll();

        }else {
            boardList.addAll(boardRepository.findByUser(user));
            boardList.addAll(Objects.requireNonNull(getBoardsByInvite(user)));
        }

        return boardList.stream()
                .map(BoardResponseDto::new)
                .toList();
    }

    @Transactional
    public BoardResponseDto updateBoard(BoardRequestDto boardRequestDto, Long boardId, User user) {
        Board board = boardRepository.findById(boardId).orElseThrow(()->
                new CustomException(ErrorCode.NOT_FOUND_BOARD));

        board.update(boardRequestDto);

        return new BoardResponseDto(board);
    }

    public void deleteBoard(Long boardId, User user) {
        Board board = boardRepository.findById(boardId).orElseThrow(()->
                new CustomException(ErrorCode.NOT_FOUND_BOARD));
        boardRepository.delete(board);
    }
    public InviteResponseDto inviteUser(InviteRequestDto inviteRequestDto, Long boardId, User user) {
        User invitedUser = userRepository.findByEmail(inviteRequestDto.getEmail()).orElseThrow(() ->
                new CustomException(ErrorCode.USERNAME_NOT_FOUND));

        Board board = boardRepository.findById(boardId).orElseThrow(()->
                new CustomException(ErrorCode.NOT_FOUND_BOARD));

        Invite invite = Invite.builder().
                user(invitedUser).
                board(board).
                build();

        if (!isAlreadyInvite(board, invitedUser)) {
            inviteRepository.save(invite);
        }

        return new InviteResponseDto(invite);
    }

    private List<Board> getBoardsByInvite(User user) {
        List<Invite> invites = inviteRepository.findByUser(user);
        return invites.stream()
                .map(Invite::getBoard)
                .collect(Collectors.toList());
    }

    private boolean isAlreadyInvite(Board board, User user){
        Optional<Invite> optionalInvite = inviteRepository.findByBoardAndUser(board, user);
        if(optionalInvite.isPresent()){
            throw new CustomException(ErrorCode.BAD_INVITE);
        }
        return false;
    }
}
