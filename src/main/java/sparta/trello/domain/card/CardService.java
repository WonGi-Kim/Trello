package sparta.trello.domain.card;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import sparta.trello.domain.board.Board;
import sparta.trello.domain.board.BoardRepository;
import sparta.trello.domain.status.Status;
import sparta.trello.domain.status.StatusRepository;
import sparta.trello.domain.user.User;
import sparta.trello.domain.user.UserRepository;
import sparta.trello.global.exception.CustomException;
import sparta.trello.global.exception.ErrorCode;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class CardService {
    private final CardRepository cardRepository;
    private final StatusRepository statusRepository;
    private final BoardRepository boardRepository;
    private final UserRepository userRepository;

    public CardResponseDto create(CardRequestDto requestDto, Long columnId, Long boardId) {
        if(requestDto.getTitle() == null){
            throw new CustomException(ErrorCode.CARD_TITLE_NOT_FOUND);
        }

        Status status = statusRepository.findById(columnId).orElseThrow(
                () -> new IllegalArgumentException("유효하지 않은 칼럼입니다.")
        );

        Board board = boardRepository.findById(boardId).orElseThrow(
                ()-> new IllegalArgumentException("유효하지 않은 boardId입니다.")
        );

        User user = userRepository.findById(1L).orElseThrow(
                ()->new IllegalArgumentException("유효하지 않은 userId입니다")
        );

        int size = cardRepository.findAll().size();
        int seq = size * 1024;

        Card card = Card.builder()
                .status(status)
                .board(board)
                .user(user)
                .sequence(seq)
                .content(requestDto.getContent())
                .title(requestDto.getTitle())
                .deadline(requestDto.getDeadline())
                .build();

        cardRepository.save(card);

        return new CardResponseDto(requestDto.getContent(), requestDto.getTitle(), requestDto.getDeadline(), status, user);
    }



}
