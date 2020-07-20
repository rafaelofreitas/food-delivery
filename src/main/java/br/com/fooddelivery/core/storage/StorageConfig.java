package br.com.fooddelivery.core.storage;

import br.com.fooddelivery.domain.service.PhotoStorageService;
import br.com.fooddelivery.domain.service.impl.PhotoStorageLocalImpl;
import br.com.fooddelivery.domain.service.impl.PhotoStorageS3Impl;
import com.amazonaws.auth.AWSStaticCredentialsProvider;
import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3ClientBuilder;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class StorageConfig {
    private final StorageProperties storageProperties;

    public StorageConfig(StorageProperties storageProperties) {
        this.storageProperties = storageProperties;
    }

    @Bean
    @ConditionalOnProperty(name = "fooddelivery.storage.type", havingValue = "s3")
    public AmazonS3 amazonS3() {
        StorageProperties.S3 s3 = this.storageProperties.getS3();

        var credentials = new BasicAWSCredentials(s3.getAccessKeyId(), s3.getAccessKeySecret());

        return AmazonS3ClientBuilder
                .standard()
                .withCredentials(new AWSStaticCredentialsProvider(credentials))
                .withRegion(s3.getRegion())
                .build();
    }

    @Bean
    public PhotoStorageService photoStorageService() {
        var type = this.storageProperties.getType();

        return type.equals(StorageProperties.StorageType.S3) ? new PhotoStorageS3Impl() : new PhotoStorageLocalImpl();
    }
}
