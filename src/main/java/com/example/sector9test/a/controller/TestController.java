package com.example.sector9test.a.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Async;
import org.springframework.security.concurrent.DelegatingSecurityContextCallable;
import org.springframework.security.concurrent.DelegatingSecurityContextExecutorService;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

@Controller
@Slf4j
public class TestController {


  /**
   *  Authentication 꺼내는 방법
   *    1. SecurityContextHolder 에서 Authentication 객체를 꺼내 사용한다.
   *    2. 매개변수로 Authentication 를 받아 사용한다.
   */
  @GetMapping("/hello")
  @ResponseBody
  public String hello(Authentication authentication) {

//    SecurityContext context = SecurityContextHolder.getContext();
//    Authentication authentication = context.getAuthentication();

    log.info("authentication.getName(): {}", authentication.getName());
    log.info("authentication.isAuthenticated(): {}", authentication.isAuthenticated());
    log.info("authentication.getAuthorities(): {}", authentication.getAuthorities());
    log.info("authentication.getCredentials(): {}", authentication.getCredentials());
    log.info("authentication.getDetails(): {}", authentication.getDetails());
    log.info("authentication.getPrincipal(): {}", authentication.getPrincipal());

    /*
      authentication.getName(): ojg
      authentication.isAuthenticated(): true
      authentication.getAuthorities(): [회원관리_C, 회원관리_R, 회원관리_U, 회원관리_D]
      authentication.getCredentials(): null
          보안상의 이유로 로그인 하면 credentials 의 값을 null 로 세팅한다.
      authentication.getDetails(): null
      authentication.getPrincipal(): User(userId=ojg, password=ojg123, userType=A, menuAuthorityList=[회원관리_C, 회원관리_R, 회원관리_U, 회원관리_D])

     */

    return "Hello " + authentication.getName() + "!";
  }

  /**
   * @Async
   *    메소드 호출이 메인 스레드의 실행 흐름을 차단하지 않고 별도의 스레드에서 독립적으로 실행되므로
   *    authentication == null 이 된다.
   */
  @GetMapping("/bye")
  @ResponseBody
  @Async
  public void goodBye() {
    Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
    String username = authentication.getName();
    log.info("@Async Test:: username :: {}", username);
  }

  /**
   * 프레임워크가 모르는 방법으로 코드가 새 스레드로 시작될 때, 보안 컨텍스트를 새로 생성한 스레드로 전파하게 도와주는 방법
   *    DelegatingSecurityContextRunnable, DelegatingSecurityContextCallable 를 사용
   *
   */
  @GetMapping("/ciao")
  @ResponseBody
  public String ciao() throws Exception {

    Callable<String> task = () -> {
      Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
      return authentication.getName();
    };

    ExecutorService executorService = Executors.newCachedThreadPool();
    try {
      var contextTask = new DelegatingSecurityContextCallable<>(task);
      return "Ciao, " + executorService.submit(contextTask).get() + "!";
    } finally {
      executorService.shutdown();
    }

  }

  /**
   * DelegatingSecurityContextExecutorService
   *    ExecutorService 를 장식하여 작업을 제출하기 전에 보안 컨텍스트 세부 정보를 다음 스레드로 전파한다.
   *
   * DelegatingSecurityContextScheduledExecutorService
   *    예약된 작업을 위해 보안 컨텍스트 전파를 구현해야 할 경우 사용한다.
   */
  @GetMapping("/hola")
  @ResponseBody
  public String hola() throws Exception {
    Callable<String> task = () -> {
      Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
      return authentication.getName();
    };
    ExecutorService e = Executors.newCachedThreadPool();
    e = new DelegatingSecurityContextExecutorService(e);

    try {
      return "Hola ," + e.submit(task).get() + "!";
    } finally {
      e.shutdown();
    }

  }

  @GetMapping("/auth")
  @ResponseBody
  public String test() {
    Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
    Object principal =  authentication.getPrincipal();
    log.info("principal = {}", principal);
    return "test";
  }



}
