package org.yapp.domain.profile.application;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import org.yapp.infra.s3.application.S3Service;

import java.io.IOException;
import java.util.UUID;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class ProfileImageService {
  private final S3Service s3Service;

  public String uploadProfileImage(MultipartFile file) throws IOException {
    String uniqueFileName = "profiles/image/" + UUID.randomUUID() + "_" + file.getOriginalFilename();
    return s3Service.upload(file, uniqueFileName);
  }
}
