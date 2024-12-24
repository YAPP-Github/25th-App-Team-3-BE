package org.yapp.domain.profile.application.util;

import java.util.List;
import java.util.Random;

public class NickNameGenerator {
  // 한글 동물 이름 10가지
  private static final List<String> ANIMAL_NAMES =
      List.of("사자", "호랑이", "곰", "독수리", "늑대", "판다", "여우", "사슴", "토끼", "돌고래");

  private static final Random RANDOM = new Random();

  public static String generateNickname() {
    String animal = ANIMAL_NAMES.get(RANDOM.nextInt(ANIMAL_NAMES.size()));
    int number = RANDOM.nextInt(10000) + 1;
    return animal + number;
  }
}
