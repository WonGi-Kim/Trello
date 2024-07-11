package sparta.trello.domain.user;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;
import sparta.trello.domain.user.dto.LoginRequestDto;
import sparta.trello.domain.user.dto.ReissueTokenRequestDto;
import sparta.trello.domain.user.dto.TokenDto;
import sparta.trello.global.exception.CustomException;
import sparta.trello.global.exception.ErrorCode;
import sparta.trello.global.security.JwtProvider;
import sparta.trello.global.security.UserPrincipal;

import java.util.Date;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class AuthService {

    private final AuthenticationManager authenticationManager;
    private final TokenRepository tokenRepository;
    private final JwtProvider jwtProvider;

    @Value("${jwt.refresh-expire-time}")
    private Long EXPIRE_TIME;

    public TokenDto login(LoginRequestDto requestDto) {

        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        requestDto.getEmail(),
                        requestDto.getPassword(),
                        null
                )
        );

        User user = ((UserPrincipal)authentication.getPrincipal()).getUser();

        Token refreshToken = issueRefreshToken(user);
        Token savedRefreshToken = tokenRepository.save(refreshToken);

        String accessToken = jwtProvider.generateToken(user.getEmail());

        return new TokenDto(accessToken, savedRefreshToken.getToken());

    }

    public void logout(User user) {

        tokenRepository.findByUserId(user.getId()).ifPresent(tokenRepository::delete);

    }

    public TokenDto reissueToken(ReissueTokenRequestDto requestDto) {

        Token refreshToken = tokenRepository.findByToken(requestDto.getRefresh()).orElseThrow(
                () -> new CustomException(ErrorCode.NOT_FOUND_REFRESH_TOKEN)
        );

        if(expired(refreshToken)) {
            throw new CustomException(ErrorCode.TOKEN_EXPIRED);
        }

        User user = refreshToken.getUser();

        Date expireDate = new Date(new Date().getTime() + EXPIRE_TIME);
        refreshToken.updateExpires(expireDate);
        Token savedRefreshToken = tokenRepository.save(refreshToken);

        String accessToken = jwtProvider.generateToken(user.getEmail());

        return new TokenDto(accessToken, savedRefreshToken.getToken());

    }

    private Token issueRefreshToken(User user) {

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

    private boolean expired(Token token) {

        return token.getExpires().getTime() < new Date().getTime();

    }

}
