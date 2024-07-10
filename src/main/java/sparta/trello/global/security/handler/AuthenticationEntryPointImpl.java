package sparta.trello.global.security.handler;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import sparta.trello.global.common.CommonErrorResponse;
import sparta.trello.global.security.JwtProvider;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.time.LocalDateTime;

public class AuthenticationEntryPointImpl implements AuthenticationEntryPoint {

    @Override
    public void commence(HttpServletRequest request, HttpServletResponse response, AuthenticationException authException)
            throws IOException, ServletException {

        boolean jwtError = request.getAttribute("error") != null;

        String error = jwtError ? request.getAttribute("error").toString() : HttpStatus.UNAUTHORIZED.getReasonPhrase();
        String message = jwtError ? error : "username 또는 password 가 잘못되었습니다.";

        response.setStatus(HttpStatus.UNAUTHORIZED.value());
        response.setContentType(MediaType.APPLICATION_JSON_VALUE);
        response.setCharacterEncoding(StandardCharsets.UTF_8.name());

        response.getWriter().write(
                CommonErrorResponse.builder()
                        .timestamp(LocalDateTime.now())
                        .path(request.getRequestURI())
                        .status(HttpStatus.UNAUTHORIZED.value())
                        .error(error)
                        .msg(message)
                        .build().toString()
        );

    }

}
