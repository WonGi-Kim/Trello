package sparta.trello.domain.board;

import org.springframework.data.jpa.repository.JpaRepository;
import sparta.trello.domain.user.User;

import java.util.List;
import java.util.Optional;

public interface InviteRepository extends JpaRepository<Invite, Long> {
    List<Invite> findByUser(User user);

    Optional<Invite> findByBoardAndUser(Board board, User user);
}
