package sparta.trello.domain.board;

import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
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

    @Builder
    public Board(String boardName, String introduction) {

        this.boardName = boardName;
        this.introduction = introduction;

    }
}
