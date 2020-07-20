package br.com.fooddelivery.domain.service.impl;

import br.com.fooddelivery.core.storage.StorageProperties;
import br.com.fooddelivery.domain.exception.StorageException;
import br.com.fooddelivery.domain.service.PhotoStorageService;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.model.CannedAccessControlList;
import com.amazonaws.services.s3.model.DeleteObjectRequest;
import com.amazonaws.services.s3.model.ObjectMetadata;
import com.amazonaws.services.s3.model.PutObjectRequest;
import org.springframework.stereotype.Service;

import java.io.InputStream;

@Service
public class PhotoStorageS3Impl implements PhotoStorageService {
    private final AmazonS3 amazonS3;
    private final StorageProperties storageProperties;

    public PhotoStorageS3Impl(AmazonS3 amazonS3, StorageProperties storageProperties) {
        this.amazonS3 = amazonS3;
        this.storageProperties = storageProperties;
    }

    @Override
    public void store(NewPicture newPicture) {
        try {
            StorageProperties.S3 s3 = this.storageProperties.getS3();

            var metaData = new ObjectMetadata();
            metaData.setContentType(newPicture.getContentType());

            var putObjectRequest = new PutObjectRequest(
                    s3.getBucketName(),
                    this.filePath(newPicture.getFileName()),
                    newPicture.getInputStream(),
                    metaData
            ).withCannedAcl(CannedAccessControlList.PublicRead);

            this.amazonS3.putObject(putObjectRequest);
        } catch (Exception e) {
            throw new StorageException("Could not send file to Amazon S3", e);
        }
    }

    @Override
    public void delete(String fileName) {
        try {
            StorageProperties.S3 s3 = this.storageProperties.getS3();

            var deleteObjectRequest = new DeleteObjectRequest(s3.getBucketName(), this.filePath(fileName));

            this.amazonS3.deleteObject(deleteObjectRequest);
        } catch (Exception e) {
            throw new StorageException("Unable to delete file on Amazon S3", e);
        }
    }

    @Override
    public InputStream toRecover(String fileName) {
        return null;
    }

    private String filePath(String fileName) {
        return String.format("%s/%s", this.storageProperties.getS3().getPhotosDirectory(), fileName);
    }
}
