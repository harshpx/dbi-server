package com.dbiServer.Controllers;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.dbiServer.DTOs.CommonResponse;

@RestController
@RequestMapping("")
public class HomeController {
  @GetMapping
  public ResponseEntity<CommonResponse<String>> home() {
    return ResponseEntity.ok(new CommonResponse<>("Welcome to DBI Server!"));
  }
}
