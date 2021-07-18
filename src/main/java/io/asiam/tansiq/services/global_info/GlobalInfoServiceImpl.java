package io.asiam.tansiq.services.global_info;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Service;

import java.net.InetAddress;

@Service
public class GlobalInfoServiceImpl implements GlobalInfoService {

    @Autowired
    private Environment environment;

    @Override
    public String getServerBaseUrlAsService() {
        String appName = environment.getProperty("spring.application.name");
        return "http://" + appName + "/";
    }

    @Override
    public String getServerBaseUrlAsIP() {
        String port = environment.getProperty("server.port");
        String hostAddress = InetAddress.getLoopbackAddress().getHostAddress();
        return "http://" + hostAddress + ":" + port + "/";
    }
}
