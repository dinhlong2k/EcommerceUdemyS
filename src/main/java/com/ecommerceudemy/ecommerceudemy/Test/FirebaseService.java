package com.ecommerceudemy.ecommerceudemy.Test;

import com.ecommerceudemy.ecommerceudemy.Test.StorageStrategy;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.auth.oauth2.GoogleCredentials;
import com.google.cloud.ReadChannel;
import com.google.cloud.storage.*;
import org.apache.commons.io.IOUtils;
import org.springframework.core.env.Environment;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.PostConstruct;
import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.channels.Channels;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;


@Service
public class FirebaseService implements StorageStrategy {

    private StorageOptions storageOptions;
    private String bucketName;
    private String projectId;

    private final Environment environment;

    public FirebaseService(Environment environment) {
        this.environment = environment;
    }

    @PostConstruct
    private void initializeFireBase() throws Exception{
        bucketName=environment.getRequiredProperty("FIREBASE_BUCKET_NAME");
        projectId=environment.getRequiredProperty("FIREBASE_PROJECT_ID");

        InputStream firebaseCredential=createFirebaseCredential();
        this.storageOptions=StorageOptions.newBuilder().setProjectId(projectId)
                .setCredentials(GoogleCredentials.fromStream(firebaseCredential)).build();
    }

    public String uploadFile(MultipartFile multipartFile) throws IOException {
        File file = convertMultiPartToFile(multipartFile);
        Path filePath = file.toPath();
        String objectName = generateFileName(multipartFile);

        Storage storage = storageOptions.getService();
        Map<String,String> map=new HashMap<>();
        map.put("firebaseStorageDownloadTokens", objectName);
        BlobId blobId = BlobId.of(bucketName, objectName);
        BlobInfo blobInfo = BlobInfo.newBuilder(blobId).setMetadata(map).setContentType(multipartFile.getContentType()).build();
        storage.create(blobInfo, multipartFile.getInputStream());

        return objectName;
    }

    @Override
    public ResponseEntity<Object> downloadFile(String fileUrl, HttpServletRequest request) throws Exception {
        Storage storage = storageOptions.getService();

        Blob blob = storage.get(BlobId.of(bucketName, fileUrl));
        ReadChannel reader = blob.reader();
        InputStream inputStream = Channels.newInputStream(reader);

        byte[] content = null;

        content = IOUtils.toByteArray(inputStream);

        final ByteArrayResource byteArrayResource = new ByteArrayResource(content);

        return ResponseEntity
                .ok()
                .contentLength(content.length)
                .header("Content-type", "application/octet-stream")
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + fileUrl + "\"")
                .body(byteArrayResource);
    }

    private File convertMultiPartToFile(MultipartFile file) throws IOException {
        File convertedFile = new File(Objects.requireNonNull(file.getOriginalFilename()));
        FileOutputStream fos = new FileOutputStream(convertedFile);
        fos.write(file.getBytes());
        fos.close();
        return convertedFile;
    }

    private InputStream createFirebaseCredential() throws Exception{

        String privateKey = environment.getRequiredProperty("FIREBASE_PRIVATE_KEY").replace("\\n", "\n");

        FirebaseCredential firebaseCredential = new FirebaseCredential();
        firebaseCredential.setType(environment.getRequiredProperty("FIREBASE_TYPE"));
        firebaseCredential.setProject_id(projectId);
        firebaseCredential.setPrivate_key_id("FIREBASE_PRIVATE_KEY_ID");
        firebaseCredential.setPrivate_key(privateKey);
        firebaseCredential.setClient_email(environment.getRequiredProperty("FIREBASE_CLIENT_EMAIL"));
        firebaseCredential.setClient_id(environment.getRequiredProperty("FIREBASE_CLIENT_ID"));
        firebaseCredential.setAuth_uri(environment.getRequiredProperty("FIREBASE_AUTH_URI"));
        firebaseCredential.setToken_uri(environment.getRequiredProperty("FIREBASE_TOKEN_URI"));
        firebaseCredential.setAuth_provider_x509_cert_url(environment.getRequiredProperty("FIREBASE_AUTH_PROVIDER_X509_CERT_URL"));
        firebaseCredential.setClient_x509_cert_url(environment.getRequiredProperty("FIREBASE_CLIENT_X509_CERT_URL"));

        //serialize with Jackson
        ObjectMapper mapper = new ObjectMapper();
        String jsonString = mapper.writeValueAsString(firebaseCredential);

        //convert jsonString string to InputStream using Apache Commons
        return IOUtils.toInputStream(jsonString);
    }

    private String generateFileName(MultipartFile multipartFile){
        return new Date().getTime() + "_" + Objects.requireNonNull(multipartFile.getOriginalFilename()).replace(" ","_");
    }

}