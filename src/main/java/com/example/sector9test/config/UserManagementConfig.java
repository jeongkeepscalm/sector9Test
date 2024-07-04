package com.example.sector9test.config;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.NoOpPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.JdbcUserDetailsManager;

import javax.sql.DataSource;

//@Configuration
@RequiredArgsConstructor
public class UserManagementConfig {

  private final DataSource dataSource;

  /**
   * UserDetailsService & PasswordEncoder 구현 재정의
   *
   *    스프링 시큐리티에 존재하는 객체를 아키텍처에 연결하는 방법을 배운다.
   *
   *    userDetailsService():
   *       더 이상 콘솔에 자동 생성된 암호 출력 x
   *       1. 자격증명이 있는 사용자 만든다.
   *       2. 사용자를 UserDetailsService 에서 관리하도록 한다.
   *       3. 주어진 암호를 UserDetailsService 가 저장하고 관리하는 암호를 이용해 검증하는 passwordEncoder 형식의 빈을 정의한다.
   *
   *       UserDetailsService 를 재정의 하면 PasswordEncoder 도 다시 선언 필요
   */
  // @Bean
  public UserDetailsService userDetailsService() throws Exception {


    /*
      var userDetailsService = new InMemoryUserDetailsManager();
      var ojg = User.withUsername("ojg")
              .password("ojg123")
              .authorities("read")
              .build();
      userDetailsService.createUser(ojg);
    */

    /*
      애플리케이션의 책임을 분리해서 작성하는 것이 좋기 때문에 권장 x

      auth.inMemoryAuthentication()
            .withUser("ojg")
            .password("ojg123")
            .authorities("read")
            .and()
            .passwordEncoder(passwordEncoder());
     */

    /*
      UserDetails user = User.builder().username("ojg").password("ojg123").authority("read").build();
      List<UserDetails> users = List.of(user);
      return new InMemoryUserDetailService(users);
    */

    String usersByUsernameQuery = "select username, password, enabled from users where username = ?";
    String authsByUserQuery = "select username, authority from authorities where username = ?";

    var jdbcUserDetailsManager = new JdbcUserDetailsManager(dataSource);
    jdbcUserDetailsManager.setUsersByUsernameQuery(usersByUsernameQuery);
    jdbcUserDetailsManager.setAuthoritiesByUsernameQuery(authsByUserQuery);

    // ... 진행중에 멈춤

    return jdbcUserDetailsManager;

  }

//  @Bean
  public PasswordEncoder passwordEncoder() {
    return NoOpPasswordEncoder.getInstance();
  }

}
