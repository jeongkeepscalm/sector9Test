package com.example.sector9test.a.exception;

import com.example.sector9test.a.ErrorCode;
import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.servlet.http.HttpServletRequest;
import lombok.Getter;
import org.springframework.http.HttpStatus;
import org.springframework.validation.BindingResult;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Getter
public class ExceptionResponse {
  private final String path;
  private final int code;
  private final String message;
  private final List<ValidationError> fieldErrors;
  @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss")
  private final LocalDateTime timestamp;
  private Object data;

  private ExceptionResponse(HttpServletRequest request, HttpStatus status, ErrorCode errorCode) {
    this.path = request.getRequestURI();
    this.code = status.value();
    this.message = errorCode.getMessage();
    this.fieldErrors = new ArrayList<>();
    this.timestamp = LocalDateTime.now();
  }

  private ExceptionResponse(HttpServletRequest request, HttpStatus status, ErrorCode errorCode, List<ValidationError> fieldErrors) {
    this.path = request.getRequestURI();
    this.code = status.value();
    this.message = errorCode.getMessage();
    this.fieldErrors = fieldErrors;
    this.timestamp = LocalDateTime.now();
  }

  private ExceptionResponse(HttpServletRequest request, HttpStatus status, String message) {
    this.path = request.getRequestURI();
    this.code = status.value();
    this.message = message;
    this.fieldErrors = new ArrayList<>();
    this.timestamp = LocalDateTime.now();
  }

  private ExceptionResponse(HttpServletRequest request, HttpStatus status, String message, Object data) {
    this.path = request.getRequestURI();
    this.code = status.value();
    this.message = message;
    this.fieldErrors = new ArrayList<>();
    this.timestamp = LocalDateTime.now();
    this.data = data;
  }

  public static ExceptionResponse of(HttpServletRequest request, HttpStatus status, String message) {
    return new ExceptionResponse(request, status, message);
  }

  public static ExceptionResponse of(HttpServletRequest request, HttpStatus status, ErrorCode errorCode) {
    return new ExceptionResponse(request, status, errorCode);
  }

  public static ExceptionResponse of(HttpServletRequest request, HttpStatus status, ErrorCode errorCode, BindingResult bindingResult) {
    return new ExceptionResponse(request, status, errorCode, ValidationError.of(bindingResult));
  }

  public static ExceptionResponse of(HttpServletRequest request, HttpStatus status, String message, Object data) {
    return new ExceptionResponse(request, status, message, data);
  }

  @Getter
  public static class ValidationError {
    private final String field;
    private final String value;
    private final String message;

    private ValidationError(String field, String value, String message) {
      this.field = field;
      this.value = value;
      this.message = message;
    }

    public static List<ValidationError> of(String field, String value, String message) {
      List<ValidationError> errors = new ArrayList<>();
      errors.add(new ValidationError(field, value, message));
      return errors;
    }

    public static List<ValidationError> of(BindingResult bindingResult) {
      return bindingResult.getFieldErrors().stream()
              .map(error -> new ValidationError(
                      error.getField(),
                      error.getRejectedValue() == null ? "" : error.getRejectedValue().toString(),
                      error.getDefaultMessage()
              ))
              .toList();
    }
  }

}