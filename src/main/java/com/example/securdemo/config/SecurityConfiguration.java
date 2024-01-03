package com.example.securdemo.config;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpStatus;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.mapping.GrantedAuthoritiesMapper;
import org.springframework.security.oauth2.client.registration.ClientRegistrationRepository;
import org.springframework.security.oauth2.client.web.OAuth2AuthorizedClientRepository;
import org.springframework.security.oauth2.client.web.reactive.function.client.ServletOAuth2AuthorizedClientExchangeFilterFunction;
import org.springframework.security.oauth2.core.oidc.user.OidcUserAuthority;
import org.springframework.security.oauth2.core.user.OAuth2UserAuthority;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.logout.HttpStatusReturningLogoutSuccessHandler;
import org.springframework.web.reactive.function.client.WebClient;

import java.util.stream.Collectors;

import static com.example.securdemo.config.Role.ADMIN;

/**
 * @see <a href="https://www.baeldung.com/spring-boot-keycloak">Baeldung spring-boot-keycloak</a>
 */
@Configuration
@EnableWebSecurity
@EnableMethodSecurity
@RequiredArgsConstructor
public class SecurityConfiguration {
    private final KeycloakLogoutHandler keycloakLogoutHandler;

    // See https://spring.io/blog/2022/02/21/spring-security-without-the-websecurityconfigureradapter
    @Bean
    public SecurityFilterChain filterChain(final HttpSecurity http) throws Exception {
        http
            .csrf(AbstractHttpConfigurer::disable)
            .authorizeHttpRequests((authorizeRequests) -> authorizeRequests
                .requestMatchers("/api/**").permitAll() // TODO
                .requestMatchers("/users/registration", "/v3/api-docs/**", "/swagger-ui/**").permitAll()
                .requestMatchers("/users/{\\d+}/delete").hasAuthority(ADMIN.getAuthority())
                .requestMatchers("/admin/**").hasAuthority(ADMIN.getAuthority())
                .anyRequest().authenticated()
            )
//            .oauth2Login(login -> login
//                .defaultSuccessUrl("/"))
            .oauth2Login((oauth2Login) -> oauth2Login
                .userInfoEndpoint((userInfo) -> userInfo.userAuthoritiesMapper(grantedAuthoritiesMapper())
                ))
            .logout(logout -> logout
                .addLogoutHandler(this.keycloakLogoutHandler)
                .logoutSuccessHandler(new HttpStatusReturningLogoutSuccessHandler(HttpStatus.OK))
//                .logoutSuccessUrl("/") // ignored if logoutSuccessHandler() is used
                .permitAll());

        return http.build();
    }

    private GrantedAuthoritiesMapper grantedAuthoritiesMapper() {
        return (authorities) -> authorities.stream().map(this::mapGrantedAuthority).collect(Collectors.toSet());
    }

    private GrantedAuthority mapGrantedAuthority(final GrantedAuthority authority) {
        return switch (authority) {
            case OidcUserAuthority userAuthority -> new OidcUserAuthority("ROLE_USER", userAuthority.getIdToken(),
                userAuthority.getUserInfo());
            case OAuth2UserAuthority userAuthority ->
                new OAuth2UserAuthority("ROLE_USER", userAuthority.getAttributes());
            case null, default -> authority;
        };
    }

    /**
     * @see <a href="https://habr.com/ru/company/otus/blog/541404/">Шпаргалка по Spring Boot WebClient</a>
     */
    @Bean
    WebClient oAuth2WebClient(final ClientRegistrationRepository clientRegistrationRepository,
                              final OAuth2AuthorizedClientRepository authorizedClientRepository) {
        var filterFunction = new ServletOAuth2AuthorizedClientExchangeFilterFunction(clientRegistrationRepository,
            authorizedClientRepository);

        filterFunction.setDefaultOAuth2AuthorizedClient(true);
        return WebClient.builder()
                        .apply(filterFunction.oauth2Configuration())
                        .build();
    }

}


