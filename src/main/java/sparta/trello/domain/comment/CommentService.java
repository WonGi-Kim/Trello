package sparta.trello.domain.comment;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import sparta.trello.domain.card.Card;
import sparta.trello.domain.card.CardRepository;
import sparta.trello.domain.comment.dto.CommentRequestDto;
import sparta.trello.global.exception.CustomException;
import sparta.trello.global.exception.ErrorCode;

@Service
@RequiredArgsConstructor
public class CommentService {
    private final CardRepository cardRepository;
    private final CommentRepository commentRepository;

    public Comment addComment(Long cardId, CommentRequestDto requestDto) {
        // 카드 조회
        Card card = cardRepository.findById(cardId)
                .orElseThrow(() -> new CustomException(ErrorCode.NOT_FOUND_CARD));

        // 사용자 조회 (로그인 체크)
//        User user = userRepository.findById(userId)
//                .orElseThrow(() -> new CustomException(ErrorCode.UNAUTHORIZED));

        // 댓글 생성
        Comment comment = Comment.builder()
                .card(card)
                .content(requestDto.getContent())
                .build();

        return commentRepository.save(comment);
    }
}
