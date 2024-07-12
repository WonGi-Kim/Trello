package sparta.trello.domain.card;

import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import org.springframework.util.StringUtils;
import sparta.trello.domain.card.dto.CardSearchCond;

import java.util.List;

import static sparta.trello.domain.card.QCard.card;

@Repository
@RequiredArgsConstructor
public class CardRepositoryQueryImpl implements CardRepositoryQuery{

    private final JPAQueryFactory queryFactory;

    @Override
    public List<Card> findBySearchCond(Long boardId, CardSearchCond searchCond){

        // 1. Board ID가 일치 해야 합니다.
        // 2. status 이름이 일치 해야 합니다.
        // 3. 담당자 nickname이 일치해야 합니다.

        /**
         * select *
         * from Card
         * where card.board.Id = boardId
         * and card.status.title = statusTitle
         * and card.User.nickname = nickname
         */

        return queryFactory.selectFrom(card)
                .where(card.board.id.eq(boardId)
                        ,eqStatus(searchCond.getStatus())
                        ,eqNickname(searchCond.getNickname()))
                .fetch();
    }

    private BooleanExpression eqStatus(String status){
        return StringUtils.hasText(status) ? card.status.title.eq(status) : null;
    }

    private BooleanExpression eqNickname(String nickname){
        return StringUtils.hasText(nickname) ? card.user.nickname.eq(nickname) : null;
    }

}
