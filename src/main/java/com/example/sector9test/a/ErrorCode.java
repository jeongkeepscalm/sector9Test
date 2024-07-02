package com.example.sector9test.a;

import jakarta.annotation.PostConstruct;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.context.MessageSource;

import java.util.Arrays;
import java.util.Locale;

@Getter
public enum ErrorCode {

  SUCCESS("common.message.success"),
  FAIL("common.message.fail"),
  NOT_FOUND("common.message.notfound"),
  DB_ERROR("common.message.dberror"),
  NOT_EXCEL_FILE("common.message.notexcelfile"),
  EMPTY_FILE("common.message.emptyfile"),
  /* 서비스 */
  NOT_TYPE("common.message.nottype")
  ;

  private String message;

  ErrorCode(String message) {
    this.message = message;
  }

  @RequiredArgsConstructor
//  @Component
  public static class ErrorMessages {

    private final MessageSource messageSource;
    private final String locale = "ko-KR";

    @PostConstruct
    public void postConstruct() {
      Arrays.stream(ErrorCode.values())
              .forEach(errorcode -> errorcode.message = messageSource.getMessage(errorcode.getMessage(), null, Locale.forLanguageTag(locale)));
    }
  }


}
