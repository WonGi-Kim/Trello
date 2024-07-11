package sparta.trello.domain.user;

import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.Date;

@Getter
@Entity
@NoArgsConstructor
public class Token {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    @Column(name = "token_id")
    private Long id;

    @Column
    private String token;

    @Column
    private Date expires;

    @OneToOne
    @JoinColumn(name = "user_id")
    private User user;

    @Builder
    public Token(String token, Date expires, User user) {

        this.token = token;
        this.expires = expires;
        this.user = user;

    }

    public void updateExpires(Date expires) {
        this.expires = expires;
    }

}
