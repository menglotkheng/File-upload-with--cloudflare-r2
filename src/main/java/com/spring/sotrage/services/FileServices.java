package com.spring.sotrage.services;

import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

public interface FileServices {

    String uploadFile(MultipartFile file) throws IOException;
    byte[] getFile(String fileKey) throws IOException;
    String getContentType(String fileName);


}
