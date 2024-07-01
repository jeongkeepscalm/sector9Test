package com.example.sector9test.a;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class MapperTest {

  @Autowired
  UserMapper mapper;

  @Test
  void fd() {
    boolean b = mapper.existsByUserId("ojg");
    Assertions.assertThat(b).isTrue();
  }

  @Test
  void ttt () {
    String a = "a";
    Assertions.assertThat(a).isEqualTo(a);
  }
}
