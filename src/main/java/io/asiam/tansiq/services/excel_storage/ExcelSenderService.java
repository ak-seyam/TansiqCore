package io.asiam.tansiq.services.excel_storage;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class ExcelSenderService {
    private final RestTemplate restTemplate;

    @Autowired
    public ExcelSenderService(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    public String ping() {
        ResponseEntity<String> res = restTemplate.getForEntity("http://excel-uploader/ping", String.class);
        return res.getBody();
    }
}
