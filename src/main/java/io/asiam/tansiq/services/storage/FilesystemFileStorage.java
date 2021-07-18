package io.asiam.tansiq.services.storage;

import io.asiam.tansiq.exceptions.StorageException;
import io.asiam.tansiq.exceptions.UserInputException;
import io.github.cdimascio.dotenv.Dotenv;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.Objects;

@Service("filesystemFileStorage")
public class FilesystemFileStorage implements StorageService {
    private final Path rootLocation;

    @Autowired
    public FilesystemFileStorage(Dotenv dotenv) {
        var storageFolder = dotenv.get("STUDENTS_FILE_PATH");
        if (storageFolder == null) {
            throw new IllegalStateException("Students file path shouldn't be null");
        }
        this.rootLocation = Paths.get(storageFolder);
    }

    @Override
    public void store(MultipartFile file) {
        if (file.isEmpty()) {
            throw new UserInputException("Cannot save an empty file");
        }
        Path destinationFilePath = this.rootLocation.resolve(
                Paths.get(Objects.requireNonNull(file.getOriginalFilename())))
                .normalize().toAbsolutePath();
        // security check
        if (!destinationFilePath.getParent().equals(this.rootLocation.toAbsolutePath())) {
            // This is a security check
            throw new IllegalStateException(
                    "Cannot store file outside current directory.");
        }
        try (InputStream inputStream = file.getInputStream()) {
            Files.copy(inputStream, destinationFilePath, StandardCopyOption.REPLACE_EXISTING);
        } catch (IOException e) {
            throw new StorageException("Couldn't store the file", e);
        }
    }

    @Override
    public Path load(String fileName) {
        return rootLocation.resolve(fileName);
    }
}
