package com.example.sector9test.a.exception;

import com.example.sector9test.a.res.ResponseCode;
import com.example.sector9test.a.res.ResponseMessage;
import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

@Getter
@AllArgsConstructor
public class ResponseDto {

  private String code;
  private String message;

  public ResponseDto() {
    this.code = ResponseCode.SUCCESS;
    this.message = ResponseMessage.SUCCESS;
  }

  public static ResponseEntity<ResponseDto> databaseError() {
    ResponseDto responseBody = new ResponseDto(ResponseCode.DATABASE_ERROR, ResponseMessage.DATABASE_ERROR);
    return new ResponseEntity<>(responseBody, HttpStatus.INTERNAL_SERVER_ERROR);
  }   //  공통 에러

  public static ResponseEntity<ResponseDto> validationFail() {
    ResponseDto responseBody = new ResponseDto(ResponseCode.VALIDATION_FAIL, ResponseMessage.VALIDATION_FAIL);
    return new ResponseEntity<>(responseBody, HttpStatus.BAD_REQUEST);
  }   //  공통 에러

}