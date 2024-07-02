package com.example.sector9test.config;

import com.example.sector9test.b.CustomAuthenticationProvider;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

@Configuration
@EnableMethodSecurity
@RequiredArgsConstructor
public class WebSecurityConfig {

  private final HttpSecurity httpSecurity;
  private final CustomAuthenticationProvider customAuthenticationProvider;

  private void matcher() throws Exception {
    httpSecurity
            .authorizeHttpRequests(requests -> requests
                    .anyRequest().permitAll()
            );
  }

  @Bean
  public SecurityFilterChain securityFilterChain() throws Exception {

    matcher();

    return httpSecurity.authenticationProvider(customAuthenticationProvider).build();
  }

}
