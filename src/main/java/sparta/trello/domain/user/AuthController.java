package sparta.trello.domain.user;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import sparta.trello.domain.user.dto.LoginRequestDto;
import sparta.trello.domain.user.dto.ReissueTokenRequestDto;
import sparta.trello.domain.user.dto.TokenDto;
import sparta.trello.global.common.CommonResponse;
import sparta.trello.global.security.JwtProvider;
import sparta.trello.global.security.UserPrincipal;

@RestController
@RequiredArgsConstructor
public class AuthController {

    private final AuthService authService;

    @PostMapping("/auth/login")
    public ResponseEntity<CommonResponse<TokenDto>> login(
            @Valid @RequestBody LoginRequestDto requestDto) {

        TokenDto responseDto = authService.login(requestDto);

        HttpHeaders headers = new HttpHeaders();
        headers.add(JwtProvider.HEADER, responseDto.getAccess());

        CommonResponse<TokenDto> response = new CommonResponse<>(
                "로그인 성공",
                HttpStatus.OK.value(),
                responseDto);

        return ResponseEntity.status(HttpStatus.OK).headers(headers).body(response);

    }

    @PutMapping("/auth/logout")
    public ResponseEntity<CommonResponse<Void>> logout(
            @AuthenticationPrincipal UserPrincipal principal) {

        authService.logout(principal.getUser());

        return ResponseEntity.status(HttpStatus.OK).body(
                new CommonResponse<>(
                        "로그아웃 성공",
                        HttpStatus.OK.value(),
                        null
                ));

    }

    @PostMapping("/auth/reissue")
    public ResponseEntity<CommonResponse<TokenDto>> reissueToken(@RequestBody ReissueTokenRequestDto requestDto) {

        TokenDto responseDto = authService.reissueToken(requestDto);

        HttpHeaders headers = new HttpHeaders();
        headers.add(JwtProvider.HEADER, responseDto.getAccess());

        CommonResponse<TokenDto> response = new CommonResponse<>(
                "토큰 발급 성공",
                HttpStatus.OK.value(),
                responseDto);

        return ResponseEntity.status(HttpStatus.OK).headers(headers).body(response);

    }

}
