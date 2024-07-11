package sparta.trello.domain.user;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import sparta.trello.global.common.CommonResponse;

@RestController
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    @PostMapping("/users/signup")
    public ResponseEntity<CommonResponse<SignupResponseDto>> signup(@RequestBody SignupRequestDto requestDto) {

        return ResponseEntity.status(HttpStatus.CREATED).body(
                new CommonResponse(
                "회원가입 성공",
                HttpStatus.CREATED.value(),
                userService.signup(requestDto)
                )
        );

    }

}
