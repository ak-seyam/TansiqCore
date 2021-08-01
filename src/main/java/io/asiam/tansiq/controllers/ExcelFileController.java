package io.asiam.tansiq.controllers;

import io.asiam.tansiq.exceptions.UserInputException;
import io.asiam.tansiq.models.StudentsFileInformation;
import io.asiam.tansiq.services.excel_storage.ExcelSenderService;
import io.asiam.tansiq.services.global_info.GlobalInfoService;
import io.asiam.tansiq.services.storage.StorageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.Map;

@RestController()
public class ExcelFileController {
    private final StorageService storageService;
    private final ExcelSenderService excelSenderService;
    private final GlobalInfoService globalInfoService;

    @Autowired
    public ExcelFileController(
            @Qualifier("filesystemFileStorage") StorageService storageService,
            ExcelSenderService excelSenderService,
            GlobalInfoService globalInfoService
    ) {
        this.storageService = storageService;
        this.excelSenderService = excelSenderService;
        this.globalInfoService = globalInfoService;
    }

    @GetMapping("/studentFiles/{fileName:.+}")
    public ResponseEntity<Resource> serveFile(@PathVariable String fileName) {
        Resource file = storageService.load(fileName);
        return new ResponseEntity<>(
                file, new HttpHeaders(), HttpStatus.OK
        );
    }

    @PostMapping("/studentFiles")
    public Map<String, Object> uploadStudentsSheet(
            @RequestParam("file") MultipartFile file,
            @RequestParam("studentNameColumnName") String studentNameColumnName,
            @RequestParam("studentMarkColumnName") String studentMarkColumnName,
            @RequestParam("studentEmailColumnName") String studentEmailColumnName,
            @RequestParam("studentPasswordColumnName") String studentPasswordColumnName
    ) {
        // if thee is not sent
        if (file.getOriginalFilename() == null) {
            throw new UserInputException("File is not sent");
        }
        // check if the file format is csv
        if (!file.getOriginalFilename().endsWith(".csv")) {
            throw new UserInputException("Invalid file format");
        }
        // store the file
        String fileName = storageService.store(file);
        // after storing the file call the excel sender service to send the file to the db
        excelSenderService.inform(
                new StudentsFileInformation(
                        globalInfoService.getServerBaseUrlAsService() + "studentFiles/" + fileName,
                        studentNameColumnName,
                        studentMarkColumnName,
                        studentEmailColumnName,
                        studentPasswordColumnName
                )
        );
        return Map.of("success", true); // send success in case of success
    }
}
