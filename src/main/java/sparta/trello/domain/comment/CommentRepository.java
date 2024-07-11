package sparta.trello.domain.comment;

import org.springframework.data.jpa.repository.JpaRepository;
import sparta.trello.domain.card.Card;

import java.util.List;

public interface CommentRepository extends JpaRepository<Comment, Long> {
    List<Comment> findByCardOrderByCreatedAtDesc(Card card);
}
