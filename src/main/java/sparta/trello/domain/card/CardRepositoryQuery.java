package sparta.trello.domain.card;

import sparta.trello.domain.card.dto.CardSearchCond;

import java.util.List;

public interface CardRepositoryQuery {
    List<Card> findBySearchCond(Long boardId, CardSearchCond searchCond);
}
