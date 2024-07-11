package sparta.trello.domain.user;

import jakarta.persistence.*;
import lombok.*;
import sparta.trello.global.Timestamped;

@Getter
@Entity
@NoArgsConstructor
public class User extends Timestamped {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    @Column(name = "user_id")
    private Long id;

    @Column(nullable = false)
    private String email;

    @Column(nullable = false)
    private String password;

    @Column(nullable = false)
    private String nickname;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private Role role;

    @Builder
    public User(String email, String password, String nickname, Role role) {

        this.email = email;
        this.password = password;
        this.nickname = nickname;
        this.role = role;

    }

    @Getter
    @RequiredArgsConstructor
    public enum Role {

        USER("USER"),
        MANAGER("MANAGER");

        private final String value;

    }

}
