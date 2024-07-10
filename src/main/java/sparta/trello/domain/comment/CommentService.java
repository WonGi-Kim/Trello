package sparta.trello.domain.comment;

import org.springframework.stereotype.Service;
import sparta.trello.domain.card.Card;
import sparta.trello.domain.card.CardRepository;
import sparta.trello.domain.user.User;
import sparta.trello.domain.user.UserRepository;

@Service
public class CommentService {
    private final CardRepository cardRepository;
    private final UserRepository userRepository;
    private final CommentRepository commentRepository;

    public CommentService(CardRepository cardRepository, UserRepository userRepository, CommentRepository commentRepository) {
        this.cardRepository = cardRepository;
        this.userRepository = userRepository;
        this.commentRepository = commentRepository;
    }

    public Comment addComment(Long cardId, Long userId, CommentRequestDto requestDto) {
        Card card = cardRepository.findById(cardId)
                .orElseThrow(() -> new IllegalArgumentException("Card not found with id: " + cardId)); // cardId로 카드 찾고, 없으면 예외 던짐

        User user = userRepository.findById(userId)
                .orElseThrow(() -> new IllegalArgumentException("User not found with id: " + userId)); // userId로 사용자 찾고, 없으면 예외 던짐

        Comment comment = Comment.builder()
                .card(card) // 댓글의 카드 설정
                .user(user) // 댓글의 사용자 설정
                .content(requestDto.getContent()) // 댓글의 내용 설정
                .build(); // Comment 객체 생성

        return commentRepository.save(comment);
    }
}
