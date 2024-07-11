package sparta.trello.domain.board;

import org.springframework.data.jpa.repository.JpaRepository;
import sparta.trello.domain.user.User;

import java.util.List;

public interface InviteRepository extends JpaRepository<Board, Long> {
    List<Board> findByUser(User user);
}
