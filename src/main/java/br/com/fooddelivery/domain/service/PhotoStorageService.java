package br.com.fooddelivery.domain.service;

import lombok.Builder;
import lombok.Getter;

import java.io.InputStream;
import java.util.UUID;

public interface PhotoStorageService {
    void store(NewPicture newPicture);

    void delete(String fileName);

    InputStream toRecover(String fileName);

    default String getGenerateFileName(String fileName) {
        return UUID.randomUUID() + "_" + fileName;
    }

    @Builder
    @Getter
    class NewPicture {
        private final String fileName;
        private final InputStream inputStream;
    }
}
