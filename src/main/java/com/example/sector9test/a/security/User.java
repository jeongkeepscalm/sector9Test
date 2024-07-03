package com.example.sector9test.a.security;


import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

@Data
@ToString
@NoArgsConstructor
public class User implements UserDetails {

  private String userId;
  private String password;
  private String userType;
  private List<String> menuAuthorityList;

  @Override
  public Collection<? extends GrantedAuthority> getAuthorities() {
    return this.getMenuAuthorityList().stream()
            .map(v -> new SimpleGrantedAuthority(v))
            .collect(Collectors.toList());
  }

  @Override
  public String getUsername() {
    return this.userId;
  }

  @Override
  public boolean isAccountNonExpired() {
    return true;
  }

  @Override
  public boolean isAccountNonLocked() {
    return true;
  }

  @Override
  public boolean isCredentialsNonExpired() {
    return true;
  }

  @Override
  public boolean isEnabled() {
    return true;
  }
}
