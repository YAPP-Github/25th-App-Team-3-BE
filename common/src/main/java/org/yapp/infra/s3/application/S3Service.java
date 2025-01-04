package org.yapp.infra.s3.application;

import com.amazonaws.AmazonServiceException;
import com.amazonaws.SdkClientException;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.model.CannedAccessControlList;
import com.amazonaws.services.s3.model.ObjectMetadata;
import com.amazonaws.services.s3.model.PutObjectRequest;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class S3Service {
  private final AmazonS3 s3Client;

  @Value("${s3.bucket}")
  private String bucket;

  public void delete(String fileName) throws IOException {
    try {
      s3Client.deleteObject(bucket, fileName);
      System.out.println("파일 삭제 완료: " + fileName);
    } catch (AmazonServiceException e) {
      throw new IOException("S3에서 파일 삭제 중 서비스 오류 발생: " + e.getMessage(), e);
    } catch (SdkClientException e) {
      throw new IOException("S3에서 파일 삭제 중 클라이언트 오류 발생: " + e.getMessage(), e);
    }
  }

  public String upload(MultipartFile file, String fileName) throws IOException {
    ObjectMetadata objectMetadata = new ObjectMetadata();
    objectMetadata.setContentType(file.getContentType());
    objectMetadata.setContentLength(file.getSize());

    try {
      s3Client.putObject(new PutObjectRequest(bucket, fileName, file.getInputStream(), objectMetadata).withCannedAcl(
          CannedAccessControlList.PublicRead));
      return s3Client.getUrl(bucket, fileName).toString();

    } catch (AmazonServiceException e) {
      throw new IOException("S3에 파일 업로드 중 서비스 오류 발생: " + e.getMessage(), e);
    } catch (SdkClientException e) {
      throw new IOException("S3에 파일 업로드 중 클라이언트 오류 발생: " + e.getMessage(), e);
    } catch (IOException e) {
      throw new IOException("파일 스트림 처리 중 오류 발생: " + e.getMessage(), e);
    }
  }
}
