package sparta.trello.domain.status;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;

public interface StatusRepository extends JpaRepository<Status, Long> {
    @Query("SELECT COALESCE(MAX(s.sequence), 0) FROM Status s WHERE s.board.id = :boardId")
    int findMaxSequenceByBoardId(Long boardId);

    boolean existsByBoardIdAndTitle(Long id, String title);

    Optional<Status> findByIdAndBoardId(Long statusId, Long boardId);
    boolean existsById(Long id);
}
