package sparta.trello.domain.card;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface CardRepository extends JpaRepository <Card, Long> {
    @Query("SELECT COALESCE(MAX(c.sequence), 0) FROM Card c WHERE c.board.id = :boardId AND c.status.id = :statusId")
    int findMaxCardSizeByStatusId(Long statusId, Long boardId);


}
