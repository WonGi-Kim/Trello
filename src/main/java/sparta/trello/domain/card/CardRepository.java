package sparta.trello.domain.card;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface CardRepository extends JpaRepository <Card, Long> {
    @Query("SELECT COALESCE(MAX(c.sequence), 0) FROM Card c WHERE c.board.id = :boardId AND c.status.id = :statusId")
    int findMaxCardSizeByStatusId(Long statusId, Long boardId);

    @Query("SELECT c FROM Card c WHERE c.board.id = :boardId AND c.status.id = :statusId")
    List<Card> findCardListByStatus(Long boardId, Long statusId);

    @Query("SELECT c FROM Card c where c.board.id = :boardId AND c.user.nickname = :nickname")
    List<Card> findCardListByUser(Long boardId, String nickname);

    @Query("SELECT c FROM Card c where c.board.id = :boardId")
    List<Card> findCardList(Long boardId);

}
