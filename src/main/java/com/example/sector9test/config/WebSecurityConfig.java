package com.example.sector9test.config;

import com.example.sector9test.a.security.CustomAuthenticationProvider;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.context.DelegatingSecurityContextRepository;
import org.springframework.security.web.context.HttpSessionSecurityContextRepository;
import org.springframework.security.web.context.RequestAttributeSecurityContextRepository;

@Configuration
@EnableMethodSecurity
@RequiredArgsConstructor
public class WebSecurityConfig {

  private final HttpSecurity httpSecurity;
  private final PasswordEncoder passwordEncoder;
  private final UserDetailsService userDetailsService;
  private final CustomAuthenticationProvider customAuthenticationProvider;

  /**
   * http 보안 구성
   */

  private void http_auth() throws Exception {

    // 인증된 사용자만 접근 허용
//    httpSecurity.authorizeHttpRequests((auth) -> auth
//            .anyRequest().authenticated()
//    ).httpBasic(Customizer.withDefaults());


      // 모든 사용자 접근 허용
      httpSecurity
              .authorizeHttpRequests(requests -> requests
                      .anyRequest().permitAll());

  }

  /**
   *  AuthenticationManagerBuilder 구성
   */
  private void auth_manager_builder() throws Exception {

    AuthenticationManagerBuilder authManagerBuilder = httpSecurity.getSharedObject(AuthenticationManagerBuilder.class);

    authManagerBuilder
            .userDetailsService(userDetailsService)
            .passwordEncoder(passwordEncoder);

    authManagerBuilder.authenticationProvider(customAuthenticationProvider);

  }


  @Bean
  public SecurityFilterChain securityFilterChain() throws Exception {

    httpSecurity
            .securityContext(context ->
                    context.securityContextRepository(
                            new DelegatingSecurityContextRepository(
                                    new RequestAttributeSecurityContextRepository(),
                                    new HttpSessionSecurityContextRepository()
                            )
                    )
            );

    http_auth();
    auth_manager_builder();



    return httpSecurity.build();

  }

}
