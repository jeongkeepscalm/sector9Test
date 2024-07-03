package com.example.sector9test.a.service;

import com.example.sector9test.a.exception.ResponseDto;
import com.example.sector9test.a.dto.SignInRequestDto;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

public interface AuthService {

  ResponseDto idCheck(String id);

  ResponseDto signUp(String id);

  ResponseDto signIn(SignInRequestDto dto, HttpServletRequest request, HttpServletResponse response);

}
