package com.example.filmartstore.config;

//import com.example.filmartstore.entity.CustomOAuth2User;

import com.example.filmartstore.service.impl.UserService;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.client.registration.ClientRegistrationRepository;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import java.io.IOException;

import static org.springframework.security.config.http.SessionCreationPolicy.STATELESS;

@Configuration
@EnableWebSecurity
public class SecurityConfig {
    private final JwtAuthenticationFiller jwtAuthentication;
    private final AuthenticationProvider authenticationProvider;
    private final UserService userService;

    public SecurityConfig(JwtAuthenticationFiller jwtAuthenticationFiller,
                          AuthenticationProvider authenticationProvider,
                          UserService userService) {
        this.jwtAuthentication = jwtAuthenticationFiller;
        this.authenticationProvider = authenticationProvider;
        this.userService = userService;
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http.csrf(AbstractHttpConfigurer::disable);
        http.authorizeHttpRequests(auth -> auth
                        .requestMatchers("/**").permitAll()
                        .requestMatchers("/oauth2/authorization/google").authenticated())
                .authenticationProvider(authenticationProvider)
                .addFilterBefore(jwtAuthentication, UsernamePasswordAuthenticationFilter.class)
                .sessionManagement(session -> session.sessionCreationPolicy(STATELESS));

        http.formLogin(login -> login
                .loginPage("/filmart/login/from")
                .loginProcessingUrl("login/Form")
                .defaultSuccessUrl("/filmart/login/success", false));

        http.oauth2Login(oauth2Login -> oauth2Login
                .loginPage("/oauth2/authorization/google")
                .successHandler(new AuthenticationSuccessHandler() {
                    @Override
                    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException, ServletException {
                        userService.checkLoginGoogleAccount(response, authentication);
                    }
                }));

        return http.build();
    }
}
