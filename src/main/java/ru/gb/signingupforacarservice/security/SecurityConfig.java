package ru.gb.signingupforacarservice.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.web.SecurityFilterChain;

/**
 * Настройка для защиты приложения
 */
@Configuration
@EnableWebSecurity
public class SecurityConfig {

    /**
     * Настройка прав доступа
     * @param http объект класса HttpSecurity
     * @return список фильтров безопасности
     * @throws Exception может выбрасыть исключения типа Exception
     */
    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
                .authorizeHttpRequests((auth) -> auth
                        .requestMatchers("/master/all").permitAll()
                        .requestMatchers("/registration/all", "/registration/master/**").hasAuthority("CLIENT")
                        .requestMatchers("/registration/**", "/client/**", "/car/**", "/master/**", "/repair/**").hasAuthority("EMPLOYEE")
                        .anyRequest().hasAuthority("ADMIN")
                )
                .formLogin(Customizer.withDefaults());

        return http.build();
    }

}
