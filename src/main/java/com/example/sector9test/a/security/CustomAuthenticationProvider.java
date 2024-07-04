package com.example.sector9test.a.security;

import com.example.sector9test.a.exception.AuthBusinessException;
import com.example.sector9test.a.res.ResponseMessage;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import java.util.List;
import java.util.stream.Collectors;


/**
 * AuthenticationProvider
 */
@Slf4j
@Component
@RequiredArgsConstructor
public class CustomAuthenticationProvider implements AuthenticationProvider {

  private final PasswordEncoder passwordEncoder;        // 빈 등록 후 사용
  private final UserService userService;

  /**
   * 인증 논리 구현
   *
   * 인증이 실패하면, AuthenticationException 던진다.
   * resultType: Authentication
   * isAuthenticated(): true 반환시 다음으로 넘어간다.
   *
   * AuthenticationProvider 에서 Principal, Credential, Authorities 를 세팅해준다.
   * Authentication auth = new UsernamePasswordAuthenticationToken(Principal, Credential, Authorities);
   */
  @Override
  public Authentication authenticate(Authentication authentication) throws AuthenticationException {

    String name = authentication.getName();
    String password = authentication.getCredentials().toString();

    log.info("authentication.getName(): {}", name);                         // 입력한 id
    log.info("authentication.getCredentials().toString(): {}", password);   // 입력한 password

    UserDetails user = userService.loadUserByUsername(name);

    // 입력한 비밀번호와 DB 데이터의 비밀번호가 같다면,
    if (passwordEncoder.matches(password, user.getPassword())) {
      return new UsernamePasswordAuthenticationToken(user, password, user.getAuthorities());
    } else {
      throw new AuthBusinessException(ResponseMessage.SIGN_IN_FAIL);
    }

  }

  /**
   * Authentication 형식의 구현 추가
   *
   * 자신이 처리할 수 있는 클래스인지 확인 후,
   * 처리가능하면(true 반환) AuthenticationManager 는 해당 AuthenticationProvider 를 사용하여 인증을 시도한다.
   *
   * 해당 코드의 경우, 매개변수로 받은 authentication 클래스가 UsernamePasswordAuthenticationToken 혹은 하위 클래스일 경우 true 를 반환한다.
   */
  @Override
  public boolean supports(Class<?> authentication) {
    return UsernamePasswordAuthenticationToken.class.isAssignableFrom(authentication);
  }

  private void setAccessMenuList(String name, String password) {

    /*
      접근 가능한 메뉴 목록 세션 저장

      List<User> AccessMenuList = userService.setAccessMenuList(name, password);
      List<String> collect = AccessMenuList.stream().map(v -> v.getMenuAuthName()).collect(Collectors.toList());

      SecurityContextHolder.getContext().getAuthentication().getDetails();
      HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.currentRequestAttributes()).getRequest();
      HttpSession session = request.getSession();
      session.setAttribute("accessMenuList", collect);
    */

  }
}
