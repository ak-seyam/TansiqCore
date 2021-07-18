package io.asiam.tansiq.services.storage;

import org.springframework.core.io.Resource;
import org.springframework.web.multipart.MultipartFile;

import java.nio.file.Path;


public interface StorageService {
    String store (MultipartFile file);
    Resource load(String fileName);
}
