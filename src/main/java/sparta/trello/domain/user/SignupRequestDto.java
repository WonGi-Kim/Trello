package sparta.trello.domain.user;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.Data;

@Data
public class SignupRequestDto {

    @Email
    @NotBlank(message = " email은 비워둘 수 없습니다.")
    private String email;

    @NotBlank(message = " password는 비워둘 수 없습니다.")
    @Pattern(regexp = "^(?=.*[a-zA-Z])(?=.*[0-9])(?=.*[~!@#$%^&*()_+]).{8,15}$"
            , message = "password는 알파벳 대소문자(a~z, A~Z), 숫자(0~9), 특수문자를 포함해야 합니다.")
    private String password;

    @NotBlank(message = " nickname은 비워둘 수 없습니다.")
    private String nickname;

    private String managercode;

}
