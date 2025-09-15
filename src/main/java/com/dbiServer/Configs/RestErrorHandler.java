package com.dbiServer.Configs;

import java.io.IOException;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import com.dbiServer.DTOs.CommonResponse;

@ControllerAdvice
public class RestErrorHandler {
  @ExceptionHandler
  public ResponseEntity<CommonResponse<String>> ioExceptionHandler(IOException e) {
    CommonResponse<String> response = new CommonResponse<>(e.getMessage(),
        HttpStatus.BAD_REQUEST.value());
    return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
  }

  @ExceptionHandler
  public ResponseEntity<CommonResponse<String>> commonExceptionHandler(Exception e) {
    CommonResponse<String> response = new CommonResponse<>(e.getMessage(),
        HttpStatus.INTERNAL_SERVER_ERROR.value());
    return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
  }
}
