package sparta.trello.domain.status;

import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import sparta.trello.domain.board.Board;
import sparta.trello.domain.card.Card;
import sparta.trello.global.Timestamped;

import java.util.ArrayList;
import java.util.List;


@Entity
@Getter
@NoArgsConstructor
public class Status extends Timestamped {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    @Column(name = "status_id")
    private Long id;

    @Column(nullable = false)
    private String title;

    @Column(nullable = false)
    private int sequence;

    @ManyToOne
    @JoinColumn(name = "board_id", nullable = false)
    private Board board;

    @OneToMany(mappedBy = "status", cascade = CascadeType.ALL)
    private List<Card> cards = new ArrayList<>();

    @Builder
    public Status(String title, int sequence, Board board) {
        this.title = title;
        this.sequence = sequence;
        this.board = board;
    }

    public void setSequence(int sequence) {
        this.sequence = sequence;
    }
}
