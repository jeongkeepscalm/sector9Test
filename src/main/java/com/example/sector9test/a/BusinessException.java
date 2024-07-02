package com.example.sector9test.a;

import lombok.Getter;

@Getter
public class BusinessException extends RuntimeException {

  private Object data;

  public BusinessException(String message) {
    super(message);
  }

  public BusinessException(ErrorCode errorCode) {
    super(errorCode.getMessage());
  }

  public BusinessException(String message, Object data) {
    super(message);
    this.data = data;
  }

  public BusinessException(ErrorCode errorCode, Object data) {
    super(errorCode.getMessage());
    this.data = data;
  }
}
