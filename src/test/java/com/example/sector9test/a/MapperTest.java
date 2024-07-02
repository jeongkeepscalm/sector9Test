package com.example.sector9test.a;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;
import java.util.Optional;

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
  void fds() {
    List<User> menus = mapper.selectAccessMenus("ojg", "ojg123");
    Assertions.assertThat(menus).hasSize(4);
    menus.stream().forEach(user -> {
      System.out.println("user access menu:" + user.getMenuAuthName());
    });
  }

  @Test
  void ttt () {
    String a = "a";
    Assertions.assertThat(a).isEqualTo(a);
  }


}
