package com.example.sector9test.a;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequestMapping("/api/menu-access-test")
public class MenuAccessTestController {

  @GetMapping("/a")
  @PreAuthorize("hasAnyAuthority('A')")
  public ResponseEntity<String> a() {
    return new ResponseEntity<>("success", HttpStatus.OK);
  }

  @GetMapping("/b")
  @PreAuthorize("hasAnyAuthority('B')")
  public ResponseEntity<String> b() {
    return new ResponseEntity<>("success", HttpStatus.OK);
  }

}
