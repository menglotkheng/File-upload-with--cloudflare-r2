package com.spring.sotrage.controller;

import com.spring.sotrage.services.FileServices;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@RestController
@RequestMapping(value = "file")
@RequiredArgsConstructor
public class FileController {

    private final FileServices filerServices;

    @PostMapping(value = "/upload")
    public ResponseEntity<String> uploadFile(MultipartFile file) throws IOException {

        return ResponseEntity.ok(filerServices.uploadFile(file));
    }

    @GetMapping(value = "/get/{fileName}")
    public ResponseEntity<byte[]> getFile(@PathVariable("fileName") String fileName) throws IOException {

        byte[] imageContent = filerServices.getFile(fileName);
        return ResponseEntity.ok()
                .header("Content-Type", filerServices.getContentType(fileName))  // Assuming it's a PNG image. Adjust if necessary.
                .body(imageContent);
    }

}
