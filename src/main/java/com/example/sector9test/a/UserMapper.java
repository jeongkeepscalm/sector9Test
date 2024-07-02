package com.example.sector9test.a;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Optional;

@Mapper
public interface UserMapper {

  boolean existsByUserId(@Param("userId") String userId);

  User selectUserByUserId(@Param("userId") String userId);
  List<User> selectAccessMenus(@Param("userId") String userId, @Param("password") String password);

}
