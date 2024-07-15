package sparta.trello.domain.status;

import org.springframework.data.jpa.repository.JpaRepository;

public interface StatusRepository extends JpaRepository<Status, Long>, StatusRepositoryCustom {
    // 기존 코드 StatusRepositoryCustomImpl로 이동

    boolean existsById(Long id);
}
