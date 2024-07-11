package sparta.trello.domain.user;

import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import sparta.trello.global.exception.CustomException;
import sparta.trello.global.exception.ErrorCode;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    private final static String ADMIN_CODE = "zhemtpwnfdpsmseoajflrkdlTek";

    public SignupResponseDto signup(SignupRequestDto requestDto) {

        if(userRepository.findByEmail(requestDto.getEmail()).isPresent()) {
            throw new CustomException(ErrorCode.ALREADY_EXISTING_USER);
        }

        User.Role role = isAdmin(requestDto.getManagercode()) ? User.Role.MANAGER :User.Role.USER;

        User user = User.builder()
                .email(requestDto.getEmail())
                .password(passwordEncoder.encode(requestDto.getPassword()))
                .nickname(requestDto.getNickname())
                .role(role)
                .build();

        User savedUser = userRepository.save(user);
        return new SignupResponseDto(savedUser);

    }

    private boolean isAdmin(String managerCode) {

        if (managerCode != null) {
            return managerCode.equals(ADMIN_CODE);
        } else {
            return false;
        }

    }

    public User updateNickname(User user, String nickname) {
        user.updateNickname(nickname);
        return userRepository.save(user);
    }
}
