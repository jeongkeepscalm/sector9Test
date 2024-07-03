package com.example.sector9test.a.mapper;

import com.example.sector9test.a.security.User;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface UserMapper {

  boolean existsByUserId(@Param("userId") String userId);
  User selectUserByUserId(@Param("userId") String userId);
  List<String> selectAccessMenus(@Param("userId") String userId, @Param("password") String password);

}
