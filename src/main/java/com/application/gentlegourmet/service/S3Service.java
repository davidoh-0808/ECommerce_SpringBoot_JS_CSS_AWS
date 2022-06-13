package com.application.gentlegourmet.service;

import com.amazonaws.services.s3.AmazonS3;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

@Service
@RequiredArgsConstructor
/*
    this helped a lot ..
    reference: https://dev.to/aws-builders/integrating-your-spring-boot-project-with-amazon-s3-3e3
*/
public class S3Service {
 */

    private final AmazonS3 s3Client;

    @Value("${S3_BUCKET_NAME}")
    private String S3_BUCKET_NAME;


    //////////////////////////////// Public Method //////////////////////////////

    @Async // @Async annotation ensures that the method is executed in a different thread
    public void uploadProductImage(MultipartFile multipartFile) {
        File file = convertMultiPartFileToFile(multipartFile);
        String fileName = "product/" + file.getName();

        s3Client.putObject(
            S3_BUCKET_NAME,
            fileName,
            file
        );
    }

    @Async // @Async annotation ensures that the method is executed in a different thread
    public void uploadRecipeImage(MultipartFile multipartFile) {
        File file = convertMultiPartFileToFile(multipartFile);
        String fileName = "recipe/" + file.getName();

        s3Client.putObject(
                S3_BUCKET_NAME,
                fileName,
                file
        );
    }


    public void uploadImageTest() {
        // bucket, key, file
        s3Client.putObject(
            S3_BUCKET_NAME,
            "product/testUpload.txt",
            new File("C:\\Users\\computer\\Desktop\\testUpload.TXT")
        );
    }


    //////////////////////////////// Private Method //////////////////////////////
    private File convertMultiPartFileToFile(final MultipartFile multipartFile) {
        final File file = new File(multipartFile.getOriginalFilename());
        try (final FileOutputStream outputStream = new FileOutputStream(file)) {
            outputStream.write(multipartFile.getBytes());
        } catch (IOException e) {
            throw new RuntimeException("An Error occurred while converting the multipart file: " + e.getLocalizedMessage());
        }
        return file;
    }

}
