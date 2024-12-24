package org.yapp.domain.user.application;

import org.springframework.stereotype.Service;
import org.yapp.domain.user.User;
import org.yapp.domain.user.dao.UserRepository;
import org.yapp.error.dto.UserErrorCode;
import org.yapp.error.exception.ApplicationException;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class UserService {
  private final UserRepository userRepository;

  public User getUserById(Long userId) {
    return userRepository.findById(userId).orElseThrow(() -> new ApplicationException(UserErrorCode.NOTFOUND_USER));
  }
}
