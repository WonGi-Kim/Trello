package sparta.trello.global.security;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;
import sparta.trello.global.security.handler.AccessDeniedHandlerImpl;
import sparta.trello.global.security.handler.AuthenticationEntryPointImpl;

@Configuration
@RequiredArgsConstructor
public class SecurityConfig {

    private final JwtProvider jwtProvider;
    private final UserDetailsServiceImpl userDetailsService;

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public JwtAuthenticationFilter jwtAuthenticationFilter() {

        return new JwtAuthenticationFilter(jwtProvider, userDetailsService);

    }

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration configuration) throws Exception {

        return configuration.getAuthenticationManager();

    }

    @Bean
    AccessDeniedHandler accessDeniedHandler() {

        return new AccessDeniedHandlerImpl();

    }

    @Bean
    AuthenticationEntryPoint authenticationEntryPoint() {

        return new AuthenticationEntryPointImpl();

    }

    @Bean
    public SecurityFilterChain securityFilterChain(final HttpSecurity http) throws Exception {

        http.csrf(AbstractHttpConfigurer::disable);
        http.formLogin(AbstractHttpConfigurer::disable);

        http.sessionManagement(configurer -> configurer.sessionCreationPolicy(SessionCreationPolicy.STATELESS));

        http.exceptionHandling(e -> e
                .authenticationEntryPoint(authenticationEntryPoint()) // 401
                .accessDeniedHandler(accessDeniedHandler()) // 403
        );

        http.authorizeHttpRequests(request ->
                request
                        .requestMatchers("/auth/login").anonymous()
                        .requestMatchers("/users/signup").anonymous()
                        .requestMatchers("/auth/reissue").anonymous()
                        //card 관련 2개는 인증받고 하는 것으로 수정.
                        .requestMatchers(HttpMethod.POST, "/boards/*/status/*/cards").authenticated()
                        .requestMatchers(HttpMethod.POST, "/boards/*/cards/**").authenticated()
                        .requestMatchers(HttpMethod.POST, "/boards/**").hasRole("MANAGER")
                        .requestMatchers(HttpMethod.PUT, "/boards/**").hasRole("MANAGER")
                        .requestMatchers(HttpMethod.DELETE, "/boards/**").hasRole("MANAGER")
                        .requestMatchers("/v3/**", "/swagger-ui/**").permitAll()
                        .anyRequest().authenticated());


        http.addFilterAt(jwtAuthenticationFilter(), BasicAuthenticationFilter.class);

        return http.build();

    }

}
