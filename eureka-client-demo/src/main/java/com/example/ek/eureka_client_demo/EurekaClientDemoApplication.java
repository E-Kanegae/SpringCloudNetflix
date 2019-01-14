package com.example.ek.eureka_client_demo;

import org.springframework.boot.WebApplicationType;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.cloud.context.config.annotation.RefreshScope;

@SpringBootApplication
@RefreshScope //to reload settings of configServer
public class EurekaClientDemoApplication {

	public static void main(String[] args) {
		new SpringApplicationBuilder(EurekaClientDemoApplication.class)
			.web(WebApplicationType.SERVLET)
			.run(args);
	}

}

