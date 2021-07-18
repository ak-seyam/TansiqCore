package io.asiam.tansiq.controllers;

import io.asiam.tansiq.services.excel_storage.ExcelSenderService;
import io.asiam.tansiq.services.storage.StorageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.util.Map;

@RestController()
@RequestMapping("/students")
public class ExcelFileController {
    private final StorageService storageService;
    private final ExcelSenderService excelSenderService;

    @Autowired
    public ExcelFileController(
            @Qualifier("simpleStorageService") StorageService storageService,
            ExcelSenderService excelSenderService
    ) {
        this.storageService = storageService;
        this.excelSenderService = excelSenderService;
    }

    @PostMapping("/")
    private Map<String, Object> uploadStudentsSheet(
            @RequestParam("file") MultipartFile file,
            @RequestParam("studentNameColumnName") String studentNameColumnName,
            @RequestParam("studentMarkColumnName") String studentMarkColumnName
    ) {
        // store the file
        storageService.store(file);
        // after storing the file call the excel sender service to send the file to the db

        return Map.of("success", true); // send success in case of success
    }
}
