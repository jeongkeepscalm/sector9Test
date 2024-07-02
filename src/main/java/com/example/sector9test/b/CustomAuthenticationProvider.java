package com.example.sector9test.b;

import com.example.sector9test.a.AuthBusinessException;
import com.example.sector9test.a.CustomUserService;
import com.example.sector9test.a.User;
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
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import java.util.List;
import java.util.stream.Collectors;


@Slf4j
@Component
@RequiredArgsConstructor
public class CustomAuthenticationProvider implements AuthenticationProvider {

  private final PasswordEncoder passwordEncoder;        // 빈 등록 후 사용
  private final CustomUserService customUserService;


  @Override
  public Authentication authenticate(Authentication authentication) throws AuthenticationException {

    String name = authentication.getName();
    String password = authentication.getCredentials().toString();

    log.info("authentication.getName(): {}", name);                         // 입력한 id
    log.info("authentication.getCredentials().toString(): {}", password);   // 입력한 password

    User user = (User) customUserService.loadUserByUsername(name);

    log.info("user: {}", user);
    log.info("user.getAuthorities(): {}", user.getAuthorities());

    // 입력한 비밀번호와 데이터의 비밀번호가 같다면,
    if (passwordEncoder.matches(password, user.getPassword())) {

//      setAccessMenuList(name, password);

      UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken = new UsernamePasswordAuthenticationToken(user, password, user.getAuthorities());

      return usernamePasswordAuthenticationToken;
    } else {
      throw new AuthBusinessException(ResponseMessage.SIGN_IN_FAIL);
    }

  }

//  private void setAccessMenuList(String name, String password) {
//
//    // 접근 가능한 메뉴 목록 세션 저장
//    List<User> AccessMenuList = customUserService.setAccessMenuList(name, password);
//    List<String> collect = AccessMenuList.stream().map(v -> v.getMenuAuthName()).collect(Collectors.toList());
//
//    SecurityContextHolder.getContext().getAuthentication().getDetails();
//    HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.currentRequestAttributes()).getRequest();
//    HttpSession session = request.getSession();
//    session.setAttribute("accessMenuList", collect);
//
//  }

  @Override
  public boolean supports(Class<?> authentication) {
    return UsernamePasswordAuthenticationToken.class.isAssignableFrom(authentication);
  }
}
