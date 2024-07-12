package sparta.trello.domain.status;

import java.util.List;
import java.util.Optional;

public interface StatusRepositoryCustom {

    Optional<Status> findByIdAndBoardId(Long statusId, Long boardId);

    List<Status> findByBoardIdOrderBySequence(Long boardId);

    int findMaxSequenceByBoardId(Long boardId);

    boolean existsByBoardIdAndTitle(Long boardId, String title);

}
