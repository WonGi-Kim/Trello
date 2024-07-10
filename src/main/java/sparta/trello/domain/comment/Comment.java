package sparta.trello.domain.comment;

import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import sparta.trello.domain.card.Card;
import sparta.trello.domain.user.User;
import sparta.trello.global.Timestamped;

@Entity
@Getter
@NoArgsConstructor
public class Comment extends Timestamped {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "comment_id")
    private Long id;

    @ManyToOne
    @JoinColumn(name = "card_id", nullable = false)
    private Card card;

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @Column(nullable = false)
    private String content;

    @Builder
    public Comment(Card card, User user, String content) {
        this.card = card;
        this.user = user;
        this.content = content;
    }

}
