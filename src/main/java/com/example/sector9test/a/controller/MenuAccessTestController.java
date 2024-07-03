package com.example.sector9test.a.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequestMapping("/api")
public class MenuAccessTestController {

  @GetMapping("/a")
  @PreAuthorize("hasAnyAuthority('회원관리_R')")
  public ResponseEntity<String> a() {
    return new ResponseEntity<>("success", HttpStatus.OK);
  }

  @GetMapping("/b")
  @PreAuthorize("hasAnyAuthority('비회원관리_R')")
  public ResponseEntity<String> b() {
    return new ResponseEntity<>("success", HttpStatus.OK);
  }

}
