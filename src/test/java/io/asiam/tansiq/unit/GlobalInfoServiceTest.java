package io.asiam.tansiq.unit;

import io.asiam.tansiq.services.global_info.GlobalInfoService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.net.InetAddress;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
public class GlobalInfoServiceTest {
    @Autowired
    GlobalInfoService globalInfoService;

    @Test
    public void itShouldReturnValidServiceBaseUrl() {
        assertThat(globalInfoService.getServerBaseUrlAsService()).isEqualTo("http://core/");
    }

    @Test
    public void itShouldReturnValidIPBaseUrl() {
        String hostAddress = InetAddress.getLoopbackAddress().getHostAddress();
        System.out.println("host address" + hostAddress);
        assertThat(globalInfoService.getServerBaseUrlAsIP()).isEqualTo("http://" + hostAddress + ":8080" + "/");
    }
}
