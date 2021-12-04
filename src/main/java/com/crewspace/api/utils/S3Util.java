package com.crewspace.api.utils;

import com.amazonaws.services.s3.AmazonS3Client;
import com.amazonaws.services.s3.model.CannedAccessControlList;
import com.amazonaws.services.s3.model.DeleteObjectRequest;
import com.amazonaws.services.s3.model.PutObjectRequest;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

@RequiredArgsConstructor
@Component
public class S3Util {
    private final AmazonS3Client amazonS3Client;
    static DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyyMMddHHmmss");

    @Value("${cloud.aws.s3.bucket}")
    public String bucket;

    public String upload(MultipartFile file, String path) throws IOException {

        String fileName = LocalDateTime.now().format(formatter);

        amazonS3Client.putObject(new PutObjectRequest(bucket, path+"/"+fileName, file.getInputStream(), null)
            .withCannedAcl(CannedAccessControlList.PublicRead));

        String imageURL = amazonS3Client.getUrl(bucket, path+"/"+fileName).toString();
        return imageURL.replace(path, "thumb/"+path);
    }

    public String postUpload(MultipartFile file) throws IOException {
        return upload(file, "post");
    }

    public String profileUpload(MultipartFile file) throws IOException {
        return upload(file, "profile");
    }

    public String spaceUpload(MultipartFile file) throws IOException {
        return upload(file, "space");
    }

    public String bannerUpload(MultipartFile file) throws IOException {
        return upload(file, "spaceBanner");
    }

    public void delete(String url){
        String key = url.split("https://crewspace.s3.ap-northeast-2.amazonaws.com/")[1];
        DeleteObjectRequest deleteObjectRequest = new DeleteObjectRequest(bucket, key);
        amazonS3Client.deleteObject(deleteObjectRequest);
    }
}
