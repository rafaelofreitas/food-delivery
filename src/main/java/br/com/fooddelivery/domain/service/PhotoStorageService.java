package br.com.fooddelivery.domain.service;

import lombok.Builder;
import lombok.Getter;

import java.io.InputStream;

public interface PhotoStorageService {
    void store(NewPicture newPicture);

    @Builder
    @Getter
    class NewPicture {
        private final String fileName;
        private final InputStream inputStream;
    }
}
