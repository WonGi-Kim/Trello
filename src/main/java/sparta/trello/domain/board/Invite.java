package sparta.trello.domain.board;

import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import sparta.trello.domain.user.User;
import sparta.trello.global.Timestamped;

@Getter
@Entity
@NoArgsConstructor
public class Invite extends Timestamped {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    @Column(name = "invite_id")
    private Long id;

    @OneToOne
    @JoinColumn(name = "user_id")
    private User user;

    @OneToOne
    @JoinColumn(name = "board_id")
    private Board board;

    @Builder
    public Invite(User user, Board board) {

        this.user = user;
        this.board = board;

    }
}
