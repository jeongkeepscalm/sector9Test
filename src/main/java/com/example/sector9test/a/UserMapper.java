package com.example.sector9test.a;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

@Mapper
public interface UserMapper {

  boolean existsByUserId(@Param("userId") String userId);
}
