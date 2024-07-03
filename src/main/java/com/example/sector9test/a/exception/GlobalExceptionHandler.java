package com.example.sector9test.a.exception;

import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.core.AuthenticationException;
import org.springframework.validation.BindException;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.multipart.MaxUploadSizeExceededException;
import org.springframework.web.servlet.NoHandlerFoundException;

import java.io.IOException;

@Slf4j
@Order(2)
@RestControllerAdvice
public class GlobalExceptionHandler {

  @ExceptionHandler(NoHandlerFoundException.class)
  public ResponseEntity<ExceptionResponse> handleNoHandlerFoundException(NoHandlerFoundException e, HttpServletRequest request) {
    log.error("::handleNoHandlerFoundException::", e);
    HttpStatus status = HttpStatus.NOT_FOUND;
    ExceptionResponse exceptionResponse = ExceptionResponse.of(request, status, ErrorCode.FAIL);
    return new ResponseEntity<>(exceptionResponse, status);
  }

  @ExceptionHandler(AuthenticationException.class)
  public ResponseEntity<ExceptionResponse> handleAuthenticationException(AuthenticationException e, HttpServletRequest request) {
    log.error("::handleAuthenticationException::", e);
    HttpStatus status = HttpStatus.UNAUTHORIZED;
    ExceptionResponse exceptionResponse = ExceptionResponse.of(request, status, ErrorCode.FAIL);
    return new ResponseEntity<>(exceptionResponse, status);
  }

  @ExceptionHandler(AccessDeniedException.class)
  public ResponseEntity<ExceptionResponse> handleAccessDeniedException(AccessDeniedException e, HttpServletRequest request) {
    log.error("::handleAccessDeniedException::", e);
    HttpStatus status = HttpStatus.FORBIDDEN;
    ExceptionResponse exceptionResponse = ExceptionResponse.of(request, status, ErrorCode.FAIL);
    return new ResponseEntity<>(exceptionResponse, status);
  }

  @ExceptionHandler(MaxUploadSizeExceededException.class)
  public ResponseEntity<ExceptionResponse> handleMaxUploadSizeExceededException(MaxUploadSizeExceededException e, HttpServletRequest request) {
    log.error("::handleMaxUploadSizeExceededException::", e);
    HttpStatus status = HttpStatus.PAYLOAD_TOO_LARGE;
    ExceptionResponse exceptionResponse = ExceptionResponse.of(request, status, ErrorCode.FAIL);
    return new ResponseEntity<>(exceptionResponse, status);
  }

  @ExceptionHandler(HttpRequestMethodNotSupportedException.class)
  public ResponseEntity<ExceptionResponse> handleHttpRequestMethodNotSupportedException(HttpRequestMethodNotSupportedException e, HttpServletRequest request) {
    log.error("::handleHttpRequestMethodNotSupportedException::", e);
    HttpStatus status = HttpStatus.METHOD_NOT_ALLOWED;
    ExceptionResponse exceptionResponse = ExceptionResponse.of(request, status, ErrorCode.FAIL);
    return new ResponseEntity<>(exceptionResponse, status);
  }

  @ExceptionHandler(MethodArgumentNotValidException.class)
  public ResponseEntity<ExceptionResponse> handleMethodArgumentNotValidException(MethodArgumentNotValidException e, HttpServletRequest request) {
    log.error("::handleMethodArgumentNotValidException::", e);
    HttpStatus status = HttpStatus.BAD_REQUEST;
    ExceptionResponse exceptionResponse = ExceptionResponse.of(request, status, ErrorCode.FAIL, e.getBindingResult());
    return new ResponseEntity<>(exceptionResponse, status);
  }

  @ExceptionHandler(BindException.class)
  public ResponseEntity<ExceptionResponse> handleBindException(BindException e, HttpServletRequest request) {
    log.error("::handleBindException::", e);
    HttpStatus status = HttpStatus.BAD_REQUEST;
    ExceptionResponse exceptionResponse = ExceptionResponse.of(request, status, ErrorCode.FAIL, e.getBindingResult());
    return new ResponseEntity<>(exceptionResponse, status);
  }

  @ExceptionHandler(IOException.class)
  public ResponseEntity<ExceptionResponse> handleIOException(IOException e, HttpServletRequest request) {
    log.error("::handleIOException::", e);
    HttpStatus status = HttpStatus.INTERNAL_SERVER_ERROR;
    ExceptionResponse exceptionResponse = ExceptionResponse.of(request, status, ErrorCode.FAIL);
    return new ResponseEntity<>(exceptionResponse, status);
  }

  @ExceptionHandler(BusinessException.class)
  public ResponseEntity<ExceptionResponse> handleBusinessException(BusinessException e, HttpServletRequest request) {
    log.error("::handleBusinessException::", e);
    HttpStatus status = HttpStatus.INTERNAL_SERVER_ERROR;
    ExceptionResponse exceptionResponse = ExceptionResponse.of(request, status, e.getMessage(), e.getData());
    return new ResponseEntity<>(exceptionResponse, status);
  }

  @ExceptionHandler(AuthBusinessException.class)
  public ResponseEntity<ExceptionResponse> handleAuthBusinessException(AuthBusinessException e, HttpServletRequest request) {

    log.error("::handleAuthBusinessException::", e);
    HttpStatus status = HttpStatus.BAD_REQUEST;
    ExceptionResponse exceptionResponse = ExceptionResponse.of(request, status, e.getMessage());
    return new ResponseEntity<>(exceptionResponse, status);

  }


  @ExceptionHandler(Exception.class)
  public ResponseEntity<ExceptionResponse> handleException(Exception e, HttpServletRequest request) {
    log.error("::handleException::", e);
    HttpStatus status = HttpStatus.INTERNAL_SERVER_ERROR;
    ExceptionResponse exceptionResponse = ExceptionResponse.of(request, status, ErrorCode.FAIL);
    return new ResponseEntity<>(exceptionResponse, status);
  }



}
