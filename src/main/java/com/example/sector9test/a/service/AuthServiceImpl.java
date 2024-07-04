package com.example.sector9test.a.service;


import com.example.sector9test.a.exception.AuthBusinessException;
import com.example.sector9test.a.exception.ResponseDto;
import com.example.sector9test.a.res.ResponseCode;
import com.example.sector9test.a.res.ResponseMessage;
import com.example.sector9test.a.dto.SignInRequestDto;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.context.HttpSessionSecurityContextRepository;
import org.springframework.stereotype.Service;

@Service
@Slf4j
@RequiredArgsConstructor
public class AuthServiceImpl implements AuthService {

  private final AuthenticationManagerBuilder authenticationManagerBuilder;

  @Override
  public ResponseDto signIn(SignInRequestDto dto, HttpServletRequest request, HttpServletResponse response) {

    try {

      AuthenticationManager authenticationManager = authenticationManagerBuilder.getObject();
      Authentication authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(dto.getUserId(), dto.getPassword()));
      SecurityContext context = SecurityContextHolder.getContext();
      context.setAuthentication(authentication);

      /*

      시큐리티 6.1 이후에 formLogin 을 사용하지 않으면 Authentication 객체가 세션에 저장이 되지 않는 문제 발생하여 추가한 코드
      SecurityFilterChain 에 .httpBasic(Customizer.withDefaults()) 을 추가하여 하위 코드를 주석 처리하였다.

      HttpSessionSecurityContextRepository secRepo = new HttpSessionSecurityContextRepository();
      secRepo.saveContext(context, request, response);

      */

    } catch (Exception e) {
      log.error(":: failed sign in");
      throw new AuthBusinessException(ResponseMessage.DATABASE_ERROR);
    }
    return new ResponseDto(ResponseCode.SUCCESS, ResponseMessage.SUCCESS);
  }


  @Override
  public ResponseDto idCheck(String id) {
    return null;
  }

  @Override
  public ResponseDto signUp(String id) {
    return null;
  }


}
