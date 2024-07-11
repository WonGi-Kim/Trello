package sparta.trello.domain.comment;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import sparta.trello.domain.card.Card;
import sparta.trello.domain.card.CardRepository;
import sparta.trello.domain.comment.dto.CommentRequestDto;
import sparta.trello.domain.comment.dto.CommentResponseDto;
import sparta.trello.global.exception.CustomException;
import sparta.trello.global.exception.ErrorCode;

import java.util.List;
import java.util.stream.Collectors;

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

    public List<CommentResponseDto> getCommentsByCardId(Long cardId) {
        // 카드 조회
        Card card = cardRepository.findById(cardId)
                .orElseThrow(() -> new CustomException(ErrorCode.NOT_FOUND_CARD));

        // 댓글 조회 (최신순 정렬)
        List<Comment> comments = commentRepository.findByCardOrderByCreatedAtDesc(card);

        // CommentResponseDto 리스트로 변환
        return comments.stream()
                .map(CommentResponseDto::new)
                .collect(Collectors.toList());
    }
}
