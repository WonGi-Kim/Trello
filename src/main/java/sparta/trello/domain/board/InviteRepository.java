package sparta.trello.domain.board;

import org.springframework.data.jpa.repository.JpaRepository;

public interface InviteRepository extends JpaRepository<Board, Long> {
}
