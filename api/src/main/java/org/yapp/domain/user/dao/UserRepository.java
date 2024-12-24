package org.yapp.domain.user.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.yapp.domain.user.User;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {
  Optional<User> findByOauthId(String oauthId);
}
