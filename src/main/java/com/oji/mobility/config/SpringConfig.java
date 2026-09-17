package com.oji.mobility.config;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@RequiredArgsConstructor
@EnableMethodSecurity
public class SpringConfig {
    //비밀번호 암호화
    @Bean
    PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http.
                //인가방법
                        authorizeHttpRequests(auth -> auth
                        //모든 사용자에 대한 접근 => permitAll()
                        .requestMatchers("/", "/login", "members/register", "/css/**", "/js/**", "/error", "/favicon.ico").permitAll()
                        //최고관리자(ADMIN)에 대한 접근 => hasRole("ADMIN")
                        .requestMatchers("/adim/**").hasRole("ADMIN")
                        //회원(USER)에 대한 접근 => hasRole("USER")
                        .requestMatchers("/boards/new").authenticated()
                        .requestMatchers("/boards/edit/*", "/boards/delete/*").authenticated()
                        .anyRequest().permitAll()
                )
                //로그인 방법
                .formLogin(form -> form
                        .loginPage("/login")
                        .loginProcessingUrl("/login")
                        .defaultSuccessUrl("/")
                        .failureUrl("/login?error=true")
                )
                //로그아웃 방법
                .logout(logout -> logout
                        .logoutUrl("/logout")
                        .logoutSuccessUrl("/")
                        .invalidateHttpSession(true)
                        .deleteCookies("JSESSIONID")
                )
        //예외 페이지(403 페이지)
                .exceptionHandling(ex ->ex
                        .accessDeniedPage("/error/403"));
        return http.build();
    }
}
