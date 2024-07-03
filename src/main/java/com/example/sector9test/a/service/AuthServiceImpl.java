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

      HttpSessionSecurityContextRepository secRepo = new HttpSessionSecurityContextRepository();
      secRepo.saveContext(context, request, response);

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
