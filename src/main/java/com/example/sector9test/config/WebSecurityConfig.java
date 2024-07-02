package com.example.sector9test.config;

import com.example.sector9test.b.CustomAuthenticationProvider;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

@Configuration
@EnableMethodSecurity
@RequiredArgsConstructor
//@EnableGlobalMethodSecurity(prePostEnabled = true)
public class WebSecurityConfig {

  private final HttpSecurity httpSecurity;
  private final CustomAuthenticationProvider customAuthenticationProvider;


  @Value("${main.log-in-page}")
  private String MAIN_LOG_IN_PAGE;

  private void logOutConfig() throws Exception {
    httpSecurity
            .logout(logout -> logout
                    .logoutRequestMatcher(new AntPathRequestMatcher("/api/auth/log-out", "GET"))
                    .logoutSuccessUrl(MAIN_LOG_IN_PAGE)
                    .deleteCookies("JSESSIONID")
                    .invalidateHttpSession(true)
            ).authorizeRequests(authorize -> authorize
                    .requestMatchers("/api/auth/log-out").permitAll() // 로그아웃 URL에 대한 접근을 모든 사용자에게 허용
            );
  }

  private void matcher() throws Exception {
    httpSecurity
            .authorizeHttpRequests(requests -> requests
                    .requestMatchers("/auth").authenticated()
                    .anyRequest().permitAll()
            );
  }

  @Bean
  public SecurityFilterChain securityFilterChain() throws Exception {

    matcher();
    // logOutConfig();

    // AuthenticationProvider 인터페이스를 구현한 customAuthenticationProvider 를 주입한다.
    return httpSecurity
            .authenticationProvider(customAuthenticationProvider) // 자체 로그인 인증 공급자 설정
            .build();
  }

}
