package sparta.trello.domain.user;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import sparta.trello.global.common.CommonResponse;
import sparta.trello.global.security.JwtProvider;

@RestController
@RequiredArgsConstructor
public class AuthController {

    private final AuthService authService;

    @PostMapping("/auth/login")
    public ResponseEntity<CommonResponse<LoginResponseDto>> login(
            @Valid @RequestBody LoginRequestDto requestDto) {

        LoginResponseDto responseDto = authService.login(requestDto);

        HttpHeaders headers = new HttpHeaders();
        headers.add(JwtProvider.HEADER, responseDto.getAccess());

        CommonResponse<LoginResponseDto> response = new CommonResponse<>(
                "로그인 성공",
                HttpStatus.OK.value(),
                responseDto);

        return ResponseEntity.status(HttpStatus.OK).headers(headers).body(response);

    }

}
