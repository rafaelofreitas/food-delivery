package br.com.fooddelivery.domain.service.impl;

import br.com.fooddelivery.domain.service.PhotoStorageService;
import org.springframework.stereotype.Service;

import java.io.InputStream;

@Service
public class PhotoStorageS3Impl implements PhotoStorageService {
    private final AmazonS3 amazonS3;

    @Override
    public void store(NewPicture newPicture) {

    }

    @Override
    public void delete(String fileName) {

    }

    @Override
    public InputStream toRecover(String fileName) {
        return null;
    }
}
