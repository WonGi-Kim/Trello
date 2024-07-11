package sparta.trello.domain.card;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import sparta.trello.domain.board.Board;
import sparta.trello.domain.board.BoardRepository;
import sparta.trello.domain.card.dto.CardRequestDto;
import sparta.trello.domain.card.dto.CardResponseDto;
import sparta.trello.domain.card.dto.CardUpdateRequestDto;
import sparta.trello.domain.card.dto.CardUpdateResponseDto;
import sparta.trello.domain.status.Status;
import sparta.trello.domain.status.StatusRepository;
import sparta.trello.domain.user.User;
import sparta.trello.domain.user.UserRepository;
import sparta.trello.domain.user.UserService;
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

    private final UserService userService;

    public CardResponseDto create(CardRequestDto requestDto, Long statusId, Long boardId, User user) {

        Status status = statusRepository.findById(statusId).orElseThrow(
                () -> new CustomException(ErrorCode.NOT_FOUND_STATUS)
        );

        Board board = checkBoard(boardId);

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

    public void deleteCard(Long boardId, Long cardId, User user) {
        checkBoard(boardId);

        Card card = checkCard(cardId);

        if(!Objects.equals(user.getNickname(), card.getUser().getNickname())){
            throw new CustomException(ErrorCode.NOT_PERMISSION_DELETE);
        }

        cardRepository.delete(card);
    }

    public CardUpdateResponseDto updateCard(Long boardId, Long cardId, CardUpdateRequestDto requestDto, User user) {
        checkBoard(boardId);

        Card card = checkCard(cardId);

        if(!Objects.equals(card.getUser().getNickname(), user.getNickname())){
            throw new CustomException(ErrorCode.NOT_PERMISSION_UPDATE);
        }

        userService.updateNickname(user, requestDto.getNickname());

        card.updateContent(requestDto.getContent());
        card.updateDeadline(requestDto.getDeadline());

        cardRepository.save(card);
        return new CardUpdateResponseDto(requestDto.getContent(), requestDto.getNickname(), requestDto.getDeadline());
    }

    public Board checkBoard(Long boardId){
        Board board = boardRepository.findById(boardId).orElseThrow(
                ()-> new CustomException(ErrorCode.NOT_FOUND_BOARD)
        );
        return board;
    }

    public Card checkCard(Long cardId){
        Card card = cardRepository.findById(cardId).orElseThrow(
                () -> new CustomException(ErrorCode.NOT_FOUND_CARD)
        );

        return card;
    }

}
