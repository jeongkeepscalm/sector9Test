package com.example.sector9test.a;

import com.example.sector9test.a.security.User;
import com.example.sector9test.a.mapper.UserMapper;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

@SpringBootTest
public class MapperTest {

  @Autowired
  UserMapper mapper;

  @Test
  void sd() {
    boolean b = mapper.existsByUserId("ojg");
    Assertions.assertThat(b).isTrue();
  }


  @Test
  void ttt () {
    String a = "a";
    Assertions.assertThat(a).isEqualTo(a);
  }


}
