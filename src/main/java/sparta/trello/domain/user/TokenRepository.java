package sparta.trello.domain.user;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface TokenRepository extends JpaRepository<Token, Long> {
    Optional<Token> findByUserId(Long id);

    Optional<Token> findByToken(String refresh);
}
