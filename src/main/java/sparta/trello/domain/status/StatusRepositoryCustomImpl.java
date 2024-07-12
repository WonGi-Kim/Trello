package sparta.trello.domain.status;

import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import sparta.trello.domain.board.QBoard;

import java.util.List;
import java.util.Optional;

@Repository
@RequiredArgsConstructor
public class StatusRepositoryCustomImpl implements StatusRepositoryCustom {

    private final JPAQueryFactory queryFactory;

    @Override
    public Optional<Status> findByIdAndBoardId(Long statusId, Long boardId) {
        QBoard qBoard = QBoard.board;
        QStatus qStatus = QStatus.status;

        Status status = queryFactory
                .select(qStatus)
                .from(qStatus)
                .join(qStatus.board, qBoard)
                .where(qStatus.id.eq(statusId)
                        .and(qBoard.id.eq(boardId)))
                .fetchOne();
        return Optional.ofNullable(status);
    }

    @Override
    public List<Status> findByBoardIdOrderBySequence(Long boardId) {
        QBoard qBoard = QBoard.board;
        QStatus qStatus = QStatus.status;

        List<Status> statusList = queryFactory
                .select(qStatus)
                .from(qBoard)
                .join(qBoard.statuses, qStatus)
                .where(qBoard.id.eq(boardId))
                .orderBy(qStatus.sequence.asc())
                .fetch();

        return statusList;
    }

    @Override
    public int findMaxSequenceByBoardId(Long boardId) {
        QStatus qStatus = QStatus.status;

        Integer qSequence = queryFactory
                .select(qStatus.sequence.max())
                .from(qStatus)
                .where(qStatus.board.id.eq(boardId))
                .fetchOne();
        return (qSequence != null) ? qSequence : 0;
    }

    @Override
    public boolean existsByBoardIdAndTitle(Long boardId, String title) {
        QBoard qBoard = QBoard.board;
        QStatus qStatus = QStatus.status;

        boolean exists = queryFactory
                .select(qStatus)
                .from(qBoard)
                .join(qBoard.statuses, qStatus)
                .where(qBoard.id.eq(boardId)
                        .and(qStatus.title.eq(title)))
                .fetchFirst() != null;

        return exists;
    }
}
