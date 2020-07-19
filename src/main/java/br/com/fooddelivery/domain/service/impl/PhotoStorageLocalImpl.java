package br.com.fooddelivery.domain.service.impl;

import br.com.fooddelivery.domain.exception.StorageException;
import br.com.fooddelivery.domain.service.PhotoStorageService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.util.FileCopyUtils;

import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;

@Service
public class PhotoStorageLocalImpl implements PhotoStorageService {
    @Value("${fooddelivery.storage.local.photos-directory}")
    private Path directory;

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
    public InputStream toRecover(String fileName) {
        try {
            var pathPhoto = this.getFilePath(fileName);

            return Files.newInputStream(pathPhoto);
        } catch (IOException e) {
            throw new StorageException("Couldn't delete file!", e);
        }
    }

    private Path getFilePath(String fileName) {
        return this.directory.resolve(Path.of(fileName));
    }
}
