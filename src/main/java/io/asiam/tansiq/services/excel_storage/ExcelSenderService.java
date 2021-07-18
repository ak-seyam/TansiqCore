package io.asiam.tansiq.services.excel_storage;

import io.asiam.tansiq.exceptions.StorageException;
import io.asiam.tansiq.models.StudentsFileInformation;
import io.asiam.tansiq.models.UploaderServerResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.Objects;

@Service
public class ExcelSenderService {
    private final RestTemplate restTemplate;
    private final String excelUploaderBase;

    @Autowired
    public ExcelSenderService(RestTemplate restTemplate, Environment environment) {
        this.restTemplate = restTemplate;
        this.excelUploaderBase = environment.getProperty("application.excel-uploader-service-name");
    }

    public String ping() {
        ResponseEntity<String> res = restTemplate.getForEntity("http://" + this.excelUploaderBase + "/ping", String.class);
        return res.getBody();
    }

    public void inform(StudentsFileInformation fileInformation) {
        ResponseEntity<UploaderServerResponse> res = restTemplate.postForEntity("http://" + this.excelUploaderBase + "/sendFiles", fileInformation, UploaderServerResponse.class);
        if (!Objects.requireNonNull(res.getBody()).isSuccess()) {
            throw new StorageException(res.getBody().getMessage());
        }
    }
}
