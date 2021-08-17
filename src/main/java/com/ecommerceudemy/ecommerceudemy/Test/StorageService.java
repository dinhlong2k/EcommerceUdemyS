package com.ecommerceudemy.ecommerceudemy.Test;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;

@Service
public class StorageService {

    @Autowired
    private StorageStrategy storageStrategy;

    public FileDTO uploadFile(MultipartFile file) throws Exception {
        String uploadedFile = this.storageStrategy.uploadFile(file);
        String fileDownloadUri = uploadedFile;
        String fileName = uploadedFile;

        return new FileDTO(
                fileName,
                file.getContentType(),
                fileDownloadUri, file.getSize());
    }

    public ResponseEntity<Object> downloadFile(String fileUrl, HttpServletRequest request) throws Exception {
        return this.storageStrategy.downloadFile(fileUrl, request);
    }
}
