package com.example.sector9test.a;

public interface AuthService {

  ResponseDto idCheck(String id);

  ResponseDto signUp(String id);

  ResponseDto signIn(SignInRequestDto dto);

}
