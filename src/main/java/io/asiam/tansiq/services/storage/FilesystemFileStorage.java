package io.asiam.tansiq.services.storage;

import io.asiam.tansiq.exceptions.StorageException;
import io.asiam.tansiq.exceptions.UserInputException;
import io.github.cdimascio.dotenv.Dotenv;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.nio.file.*;
import java.util.Calendar;

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
    public String store(MultipartFile file) {
        if (file.isEmpty()) {
            throw new UserInputException("Cannot save an empty file");
        }
        String fileName = file.getOriginalFilename().replace(" ", "_");
        if (fileName == null) {
            throw new StorageException("File name cannot be null");
        }
        fileName = Calendar.getInstance().getTimeInMillis() + "_" + fileName;
        Path destinationFilePath = this.rootLocation.resolve(
                Paths.get(fileName))
                .normalize().toAbsolutePath();
        // security check
        if (!destinationFilePath.getParent().equals(this.rootLocation.toAbsolutePath())) {
            // This is a security check
            throw new IllegalStateException(
                    "Cannot store file outside current directory.");
        }
        File dir = this.rootLocation.toAbsolutePath().toFile();
        dir.mkdir();
        try (InputStream inputStream = file.getInputStream()) {
            Files.copy(inputStream, destinationFilePath, StandardCopyOption.REPLACE_EXISTING);
        }
        catch (NoSuchFileException noSuchFileException) {
            throw new StorageException("No such file Error", noSuchFileException);
        }
        catch (IOException e) {
            throw new StorageException("Couldn't store the file ", e);
        }
        return fileName;
    }

    @Override
    public Resource load(String fileName) {
        try {
            Path file = rootLocation.resolve(fileName);
            Resource resource = new UrlResource(file.toUri());
            if (resource.exists() || resource.isReadable()) {
                return resource;
            } else {
                throw new StorageException(
                        "Could not read file: " + fileName);

            }
        } catch (MalformedURLException e) {
            throw new StorageException("Could not read file (" + fileName + ")", e);
        }
    }
}
