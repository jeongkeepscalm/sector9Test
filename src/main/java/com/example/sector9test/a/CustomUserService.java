package com.example.sector9test.a;

import com.example.sector9test.a.res.ResponseMessage;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class CustomUserService implements UserDetailsService {

  private final UserMapper userMapper;

  
  // 중복 체크 후 userDTO 반환
  @Override
  public UserDetails loadUserByUsername(String userId) throws UsernameNotFoundException {
    boolean b = userMapper.existsByUserId(userId);
    if (b) {
      return userMapper.selectUserByUserId(userId);
    } else {
      log.info("this user doesn't exists");
      throw new AuthBusinessException(ResponseMessage.SIGN_IN_FAIL);
    }
  }

  public List<User> setAccessMenuList (String id, String password) {
    return userMapper.selectAccessMenus(id, password);
  }



}
