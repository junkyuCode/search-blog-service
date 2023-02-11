package com.junkyu.searchblogservice.common.error;

import com.junkyu.searchblogservice.common.response.ApiResponse;
import feign.FeignException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.validation.BindException;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

/**
 * 블로그 검색 서비스 익셉션 핸들러.
 */
@Slf4j
@RestControllerAdvice
public class GlobalExceptionHandler {

  /**
   * ContentsNotFoundException handler.
   */
  @ResponseStatus(HttpStatus.BAD_REQUEST)
  @ExceptionHandler(BindException.class)
  public ApiResponse<?> bindExceptionHandler(
      BindException e
  ) {

    log.error("BindException error", e);
    BindingResult bindingResult = e.getBindingResult();

    StringBuilder stringBuilder = new StringBuilder();

    for (FieldError fieldError : bindingResult.getFieldErrors()) {
      stringBuilder.append(fieldError.getField()).append(":");
      stringBuilder.append(fieldError.getDefaultMessage());
      stringBuilder.append(", ");
    }

    return ApiResponse.builder()
        .success(false)
        .message(stringBuilder.toString())
        .data(null)
        .build();
  }

  @ExceptionHandler(FeignException.class)
  public ApiResponse<?> feignExceptionHandler(FeignException e) {
    log.error("FeignException error", e);

    return ApiResponse.builder()
        .success(false)
        .message("외부 API 호출 오류")
        .data(null)
        .build();
  }
}
