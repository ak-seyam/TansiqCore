package io.asiam.tansiq.integration.services.excel_uploader;

import io.asiam.tansiq.services.excel_storage.ExcelSenderService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
public class UploaderUp {
    @Autowired
    ExcelSenderService storageUp;

    @Test
    public void itShouldReceivePongForPing() {
        String res = storageUp.ping();
        assertThat(res).isEqualTo("pong");
    }
}
