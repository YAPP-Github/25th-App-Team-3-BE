package org.yapp.global.application;

import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;

import java.time.Duration;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class RedisService {

  private final StringRedisTemplate redisTemplate;

  public void deleteKey(String key) {
    redisTemplate.delete(key);
  }

  public String getValue(String key) {
    return redisTemplate.opsForValue().get(key);
  }

  public void setKeyWithExpiration(String key, String value, long expiredTime) {
    redisTemplate.opsForValue().set(key, value, Duration.ofMillis(expiredTime));
  }
}
