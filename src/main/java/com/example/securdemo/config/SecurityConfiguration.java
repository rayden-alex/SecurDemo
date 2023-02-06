package com.example.securdemo.config;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpStatus;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.oauth2.client.registration.ClientRegistrationRepository;
import org.springframework.security.oauth2.client.web.OAuth2AuthorizedClientRepository;
import org.springframework.security.oauth2.client.web.reactive.function.client.ServletOAuth2AuthorizedClientExchangeFilterFunction;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.logout.HttpStatusReturningLogoutSuccessHandler;
import org.springframework.web.reactive.function.client.WebClient;

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
            .authorizeRequests(authorizeRequests -> authorizeRequests
                .antMatchers("/api/**").permitAll() // TODO
                .antMatchers("/users/registration", "/v3/api-docs/**", "/swagger-ui" +
                    "/**").permitAll()
                .antMatchers("/users/{\\d+}/delete").hasAuthority(ADMIN.getAuthority())
                .antMatchers("/admin/**").hasAuthority(ADMIN.getAuthority())
                .anyRequest().authenticated()
            )
//            .oauth2Login(login -> login
//                .defaultSuccessUrl("/"))
            .oauth2Login().and()
            .logout(logout -> logout
                .addLogoutHandler(this.keycloakLogoutHandler)
                .logoutSuccessHandler(new HttpStatusReturningLogoutSuccessHandler(HttpStatus.OK))
//                .logoutSuccessUrl("/") // ignored if logoutSuccessHandler() is used
                .permitAll());

        return http.build();
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


