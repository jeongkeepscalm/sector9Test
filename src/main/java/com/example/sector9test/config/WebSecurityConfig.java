package com.example.sector9test.config;

import com.example.sector9test.a.security.CustomAuthenticationProvider;
import com.example.sector9test.a.security.CustomEntryPoint;
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

//    httpSecurity.httpBasic(c -> {
//      c.realmName("other");
//      c.authenticationEntryPoint(new CustomEntryPoint());
//    });
//
//    httpSecurity.authorizeHttpRequests(auth -> auth.anyRequest().authenticated());


    httpSecurity.authorizeHttpRequests(auth -> auth.anyRequest().permitAll())
            .httpBasic(Customizer.withDefaults());


            /*

            질문. 설정 시 넘어가 지지 않음.

            .formLogin(form -> form
                    .loginPage("/auth/loginPage")
                    .loginProcessingUrl("/auth/sign-in")
                    .defaultSuccessUrl("/main", true)
                    .failureUrl("/auth/loginPage?error=true")
                    .permitAll())
            .csrf(c->c.disable());
            */
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

    http_auth();
    auth_manager_builder();

    return httpSecurity.build();

  }


  /**
   *  .httpBasic(Customizer.withDefaults())
   *    HTTP 기본 인증을 활성화. 활성화 해야 유저 정보가 SecurityContextHolder 에 authentication 인스턴스가 저장된다.
   *
   *  httpSecurity.authorizeRequests: 5.4 이전 버전
   *  httpSecurity.authorizeHttpRequests: 5.4 이후 버전
   *
   *
   */

}
