package com.ecommerceudemy.ecommerceudemy.Test;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.transaction.Transactional;

@RestController
@RequestMapping("/admin")
public class TestUpload {

    @Autowired
    private StorageService storageService;

    @PostMapping("/upload-image")
    @Transactional
    public ResponseEntity uploadImage(@RequestParam("file")MultipartFile multipartFile) throws Exception{
        FileDTO fileDTO = storageService.uploadFile(multipartFile);
        return new ResponseEntity<>(fileDTO, null, HttpStatus.OK);
    }

    @GetMapping("/download/{fileName:.+}")
    public ResponseEntity<Object> downloadFile(@PathVariable String fileName, HttpServletRequest request) throws Exception {
        return storageService.downloadFile(fileName, request);
    }
}
