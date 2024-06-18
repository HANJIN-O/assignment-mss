package com.musinsa.assignment;

import lombok.RequiredArgsConstructor;
import org.springframework.core.env.Environment;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.function.Function;

@RestControllerAdvice(basePackages = "com.musinsa.assignment")
@RequiredArgsConstructor
public class GlobalExceptionHandler {
  private final Function<Exception, ResponseEntity> exceptionHandlerFunction;

  @ExceptionHandler(Exception.class)
  public ResponseEntity handle(Exception e) throws Exception {
    return exceptionHandlerFunction.apply(e);
  }
}
