package sparta.trello.domain.user;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;
import sparta.trello.global.exception.CustomException;
import sparta.trello.global.security.JwtProvider;
import sparta.trello.global.security.UserPrincipal;

import java.util.Date;
import java.util.Optional;
import java.util.UUID;

import static jakarta.persistence.GenerationType.UUID;

@Service
@RequiredArgsConstructor
public class AuthService {

    private final AuthenticationManager authenticationManager;
    private final TokenRepository tokenRepository;
    private final JwtProvider jwtProvider;

    @Value("${jwt.refresh-expire-time}")
    private Long EXPIRE_TIME;

    public LoginResponseDto login(LoginRequestDto requestDto) {

        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        requestDto.getEmail(),
                        requestDto.getPassword(),
                        null
                )
        );

        User user = ((UserPrincipal)authentication.getPrincipal()).getUser();

        Token token = reissueRefreshToken(user);
        tokenRepository.save(token);

        String accessToken = jwtProvider.generateToken(authentication);
        String refreshToken = token.getToken();

        return new LoginResponseDto(accessToken, refreshToken);

    }

    private Token reissueRefreshToken(User user) {

        Date expireDate = new Date(new Date().getTime() + EXPIRE_TIME);
        Optional<Token> optionalToken = tokenRepository.findByUserId(user.getId());
        Token token;

        if (optionalToken.isPresent()) {
            token = optionalToken.get();
            token.updateExpires(expireDate);
        } else {
            token = Token.builder()
                    .user(user)
                    .token(java.util.UUID.randomUUID().toString())
                    .expires(expireDate)
                    .build();
        }

        return token;

    }
}
