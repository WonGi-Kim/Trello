package sparta.trello.domain.card;

import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import sparta.trello.domain.board.Board;
import sparta.trello.domain.comment.Comment;
import sparta.trello.domain.status.Status;
import sparta.trello.domain.user.User;
import sparta.trello.global.Timestamped;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Entity
@Getter
@NoArgsConstructor
public class Card extends Timestamped {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    @Column(name = "card_id")
    private Long id;

    @Column(nullable = false)
    private String title;


    @Column
    private String content;

    @Column
    private LocalDate deadline;

    @OneToMany(mappedBy = "card", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Comment> comments = new ArrayList<>();

    @ManyToOne
    @JoinColumn(name = "status_id", nullable = false)
    private Status status;

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @ManyToOne
    @JoinColumn(name = "board_id", nullable = false)
    private Board board;

    @Builder
    public Card(String title, String content, LocalDate deadline, Status status, User user, Board board) {
        this.title = title;
        this.content = content;
        this.deadline = deadline;
        this.status = status;
        this.user = user;
        this.board = board;
    }

    public void updateDeadline(LocalDate deadline){
        this.deadline = deadline;
    }

    public void updateTitle(String title){
        this.title = title;
    }

    public void updateContent(String content){
        this.content = content;
    }

    public void updateStatus(Status status){
        this.status = status;
    }

    public void updateUser(User newUser) {
        this.user = newUser;
    }
}
