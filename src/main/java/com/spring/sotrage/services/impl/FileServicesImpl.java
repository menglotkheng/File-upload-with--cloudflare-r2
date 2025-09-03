package com.spring.sotrage.services.impl;

import com.spring.sotrage.services.FileServices;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.multipart.MultipartFile;
import software.amazon.awssdk.core.sync.RequestBody;
import software.amazon.awssdk.services.s3.S3Client;
import software.amazon.awssdk.services.s3.model.PutObjectRequest;

import java.io.IOException;
import java.io.InputStream;
import java.util.Objects;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class FileServicesImpl implements FileServices {

    private final S3Client s3;

    @Value("${aws.s3.bucket}")
    private String bucketName;

    private final RestTemplate restTemplate;

    @Value("${cdn-storage.url}")
    private String cdnStorageUrl;

    @Override
    public String uploadFile(MultipartFile file) throws IOException {

        if (file.isEmpty()) {
            throw new IllegalArgumentException("Empty file");
        }
        
        String fileExtention = "";

        String fileName = file.getOriginalFilename();
        if (fileName != null && fileName.contains(".")) {
            fileExtention = fileName.substring(fileName.lastIndexOf(".") + 1);
        }

        String fileKey = UUID.randomUUID() + "." + fileExtention;

        PutObjectRequest putObjectRequest = PutObjectRequest.builder()
                .bucket(bucketName)
                .key(fileKey)
                .contentType(file.getContentType())
                .build();

        try (InputStream inputStream = file.getInputStream()) {
            s3.putObject(putObjectRequest, RequestBody.fromInputStream(inputStream, file.getSize()));
        }

        return "Upload file is OK";
    }

    @Override
    public byte[] getFile(String fileKey) {
        return restTemplate.getForObject(cdnStorageUrl + fileKey, byte[].class);
    }

    @Override
    public String getContentType(String fileName){

        String contentType = "application/octet-stream";

        if (fileName.endsWith(".jpg") || fileName.endsWith(".jpeg")) {
            contentType = "image/jpeg";
        } else if (fileName.endsWith(".png")) {
            contentType = "image/png";
        } else if (fileName.endsWith(".gif")) {
            contentType = "image/gif";
        } else if (fileName.endsWith(".pdf")) {
            contentType = "application/pdf";
        } else if (fileName.endsWith(".txt")) {
            contentType = "text/plain";
        } else if (fileName.endsWith(".html")) {
            contentType = "text/html";
        }

        return contentType;
    }

}
