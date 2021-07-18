package io.asiam.tansiq.services.storage;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.nio.file.Path;


public interface StorageService {
    void store (MultipartFile file);
    Path load(String fileName);
}
