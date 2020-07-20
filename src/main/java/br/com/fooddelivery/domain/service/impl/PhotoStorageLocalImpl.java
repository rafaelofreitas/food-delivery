package br.com.fooddelivery.domain.service.impl;

import br.com.fooddelivery.core.storage.StorageProperties;
import br.com.fooddelivery.domain.exception.StorageException;
import br.com.fooddelivery.domain.service.PhotoStorageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.FileCopyUtils;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

public class PhotoStorageLocalImpl implements PhotoStorageService {
    @Autowired
    private StorageProperties storageProperties;

    @Override
    public void store(NewPicture newPicture) {
        try {
            var pathPhoto = this.getFilePath(newPicture.getFileName());

            FileCopyUtils.copy(newPicture.getInputStream(), Files.newOutputStream(pathPhoto));
        } catch (
                IOException e) {
            throw new StorageException("Could not store the file!", e);
        }
    }

    @Override
    public void delete(String fileName) {
        try {
            var pathPhoto = this.getFilePath(fileName);

            Files.deleteIfExists(pathPhoto);
        } catch (IOException e) {
            throw new StorageException("Couldn't delete file!", e);
        }
    }

    @Override
    public PhotoRecover toRecover(String fileName) {
        try {
            var pathPhoto = this.getFilePath(fileName);

            return PhotoRecover
                    .builder()
                    .inputStream(Files.newInputStream(pathPhoto))
                    .build();
        } catch (IOException e) {
            throw new StorageException("Couldn't delete file!", e);
        }
    }

    private Path getFilePath(String fileName) {
        return this.storageProperties.getLocal().getPhotosDirectory().resolve(Path.of(fileName));
    }
}
