package com.example.sector9test.a.security;

import com.example.sector9test.a.exception.AuthBusinessException;
import com.example.sector9test.a.mapper.UserMapper;
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
public class UserService implements UserDetailsService {

  private final UserMapper userMapper;

  /**
   * 사용자를 찾아서 반환
   */
  @Override
  public UserDetails loadUserByUsername(String userId) throws UsernameNotFoundException {
    boolean b = userMapper.existsByUserId(userId);
    if (b) {
      User user = userMapper.selectUserByUserId(userId);
      List<String> menuList = userMapper.selectAccessMenus(user.getUserId(), user.getPassword());
      user.setMenuAuthorityList(menuList);
      return user;
    } else {
      log.info("this user doesn't exists");
      throw new AuthBusinessException(ResponseMessage.SIGN_IN_FAIL);
    }
  }


}
