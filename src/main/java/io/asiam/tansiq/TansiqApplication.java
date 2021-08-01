package io.asiam.tansiq;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.context.annotation.Bean;
import org.springframework.scheduling.annotation.EnableAsync;

@SpringBootApplication
@EnableDiscoveryClient
@EnableAsync
public class TansiqApplication {

	public static void main(String[] args) {
		SpringApplication.run(TansiqApplication.class, args);
	}

	@Bean
	CommandLineRunner runner() {
		return (args) -> {
			// create sample of course
		};
	}

}
