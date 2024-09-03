package sparta.trello.domain.status;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface StatusSecondRepository extends JpaRepository<Status, Long> {
    Optional<Status> findByIdAndBoardId(Long statusId, Long boardId);

    List<Status> findByBoardIdOrderBySequence(Long boardId);

    int findMaxSequenceByBoardId(Long boardId);

    boolean existsByBoardIdAndTitle(Long boardId, String title);
}