package com.example.sector9test.a;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.Collection;
import java.util.Iterator;
import java.util.List;

@Controller
@Slf4j
@RequiredArgsConstructor
@RequestMapping("/auth")
public class UserController {

  private final AuthService authService;

  @GetMapping(path = "/loginPage")
  public String loginPage() {
    return "/login-page";
  }

  @PostMapping(path = "/sign-in")
  public String signInMethod(@ModelAttribute("signInRequestDto") SignInRequestDto dto, HttpServletRequest request, HttpServletResponse response) {

    authService.signIn(dto, request, response);

    HttpSession session = request.getSession();
    List<String> accessMenuList = (List<String>) session.getAttribute("accessMenuList");

    log.info("accessMenuList: {}", accessMenuList);

    Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
    Collection<? extends GrantedAuthority> authorities = authentication.getAuthorities();
    Iterator<? extends GrantedAuthority> iter = authorities.iterator();
    GrantedAuthority auth = iter.next();
    String role = auth.getAuthority();

    log.info("role: {}", role);

    return "/main";
  }

  @GetMapping
  @ResponseBody
  public String test() {
    Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
    Object principal =  authentication.getPrincipal();
    log.info("principal = {}", principal);
    return "test";
  }

}
