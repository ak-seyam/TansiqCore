package io.asiam.tansiq;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
@EnableEurekaClient
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
