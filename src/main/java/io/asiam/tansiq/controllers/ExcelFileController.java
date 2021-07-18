package io.asiam.tansiq.controllers;

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

import java.nio.file.Path;
import java.util.Map;

@RestController()
@RequestMapping("/students")
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

    @GetMapping("/studentFiles/{fileName:+}")
    public ResponseEntity<Resource> serveFile(@PathVariable String fileName) {
        Resource file = storageService.load(fileName);
        return new ResponseEntity<>(
                file, new HttpHeaders(), HttpStatus.OK
        );
    }

    @PostMapping("/")
    public Map<String, Object> uploadStudentsSheet(
            @RequestParam("file") MultipartFile file,
            @RequestParam("studentNameColumnName") String studentNameColumnName,
            @RequestParam("studentMarkColumnName") String studentMarkColumnName
    ) {
        // store the file
        String fileName = storageService.store(file);
        // after storing the file call the excel sender service to send the file to the db
        // TODO fix this
        excelSenderService.inform(globalInfoService.getServerBaseUrlAsService() + "studentFiles/" + fileName);
        return Map.of("success", true); // send success in case of success
    }
}
