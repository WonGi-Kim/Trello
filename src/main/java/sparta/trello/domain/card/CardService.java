package sparta.trello.domain.card;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import sparta.trello.domain.board.Board;
import sparta.trello.domain.board.BoardRepository;
import sparta.trello.domain.card.dto.CardRequestDto;
import sparta.trello.domain.card.dto.CardResponseDto;
import sparta.trello.domain.status.Status;
import sparta.trello.domain.status.StatusRepository;
import sparta.trello.domain.user.User;
import sparta.trello.domain.user.UserRepository;
import sparta.trello.global.common.CommonResponse;
import sparta.trello.global.exception.CustomException;
import sparta.trello.global.exception.ErrorCode;

import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class CardService {
    private final CardRepository cardRepository;
    private final StatusRepository statusRepository;
    private final BoardRepository boardRepository;
    private final UserRepository userRepository;

    public CardResponseDto create(CardRequestDto requestDto, Long statusId, Long boardId) {

        Status status = statusRepository.findById(statusId).orElseThrow(
                () -> new CustomException(ErrorCode.NOT_FOUND_STATUS)
        );

        Board board = boardRepository.findById(boardId).orElseThrow(
                ()-> new CustomException(ErrorCode.NOT_FOUND_BOARD)
        );

        User user = userRepository.findById(1L).orElseThrow(
                ()-> new CustomException(ErrorCode.USERNAME_NOT_FOUND)
        );

        int size = cardRepository.findMaxCardSizeByStatusId(statusId, boardId);
        int seq = size + 1;

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

    public List<CardResponseDto> findCardList(Long boardId) {
        if(!boardRepository.existsById(boardId)){
            throw new CustomException(ErrorCode.NOT_FOUND_BOARD);
        }
        List<Card> cardList = cardRepository.findCardList(boardId);
        return cardList.stream().map(card -> new CardResponseDto(card.getContent(), card.getTitle(), card.getDeadline(), card.getStatus(), card.getUser()))
                .collect(Collectors.toList());
    }

    public List<CardResponseDto> findCardListByStatus(Long boardId, Long statusId) {
        if(!boardRepository.existsById(boardId)){
            throw new CustomException(ErrorCode.NOT_FOUND_BOARD);
        }

        if(!statusRepository.existsById(statusId)){
            throw new CustomException(ErrorCode.NOT_FOUND_STATUS);
        }

        List<Card> cardList = cardRepository.findCardListByStatus(boardId, statusId);

        return cardList.stream().map(card -> new CardResponseDto(card.getContent(), card.getTitle(), card.getDeadline(), card.getStatus(), card.getUser()))
                .collect(Collectors.toList());
    }

    public List<CardResponseDto> findCardListByUser(String nickname, Long boardId) {
        if(!userRepository.existsByNickname(nickname)){
            throw new CustomException(ErrorCode.USER_NICKNAME_NOT_FOUND);
        }

        if(!boardRepository.existsById(boardId)){
            throw new CustomException(ErrorCode.NOT_FOUND_BOARD);
        }

        List<Card> cardList = cardRepository.findCardListByUser(boardId, nickname);

        return cardList.stream().map(card -> new CardResponseDto(card.getContent(), card.getTitle(), card.getDeadline(), card.getStatus(), card.getUser()))
                .collect(Collectors.toList());
    }

    public void deleteCard(Long boardId, Long cardId) {
        Board board = boardRepository.findById(boardId).orElseThrow(
                () -> new CustomException(ErrorCode.NOT_FOUND_BOARD)
        );

        Card card = cardRepository.findByIdAndBoardId(boardId, cardId).orElseThrow(
                () -> new CustomException(ErrorCode.NOT_FOUND_CARD)
        );

        cardRepository.delete(card);
    }


}
