package org.yapp.application;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

@Service
public class AuthenticationService {
  public Authentication getAuthentication() {
    return SecurityContextHolder.getContext().getAuthentication();
  }

  public Long getUserId() {
    return (Long) getAuthentication().getPrincipal();
  }
}
