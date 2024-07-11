package sparta.trello.domain.board;

import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import sparta.trello.domain.board.dto.BoardRequestDto;
import sparta.trello.domain.user.User;
import sparta.trello.global.Timestamped;

@Entity
@Getter
@NoArgsConstructor
public class Board extends Timestamped {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    @Column(name = "board_id")
    private Long id;

    @Column(nullable = false)
    private String boardName;

    @Column(nullable = false)
    private String introduction;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    @Builder
    public Board(String boardName, String introduction, User user) {

        this.boardName = boardName;
        this.introduction = introduction;
        this.user = user;

    }

    public void update(BoardRequestDto boardRequestDto) {

        this.boardName = boardRequestDto.getBoardName();
        this.introduction = boardRequestDto.getIntroduction();

    }
}
