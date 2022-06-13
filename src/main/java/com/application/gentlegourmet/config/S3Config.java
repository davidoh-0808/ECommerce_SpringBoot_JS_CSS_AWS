package com.application.gentlegourmet.config;

import com.amazonaws.auth.AWSCredentials;
import com.amazonaws.auth.AWSStaticCredentialsProvider;
import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.regions.Regions;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3ClientBuilder;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class S3Config {

    /* get AWS credentials from application.properties file
   , create a client connection
   , and configure the S3 client */
    @Value("${AWS_ACCESS_KEY}")
    private String AWS_ACCESS_KEY;
    @Value("${AWS_SECRET_KEY}")
    private String AWS_SECRET_KEY;


    ////////////////////////////////////////////////////////////////////////////////////////


    @Bean
    public AmazonS3 getAmazonS3Client() {
        final AWSCredentials awsCredentials = new BasicAWSCredentials(AWS_ACCESS_KEY, AWS_SECRET_KEY);

        return AmazonS3ClientBuilder
                .standard()
                .withCredentials(new AWSStaticCredentialsProvider(awsCredentials))
                .withRegion(Regions.AP_NORTHEAST_2)
                .build();
    }

}
