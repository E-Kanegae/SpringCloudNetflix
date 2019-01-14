package com.example.ek.eureka_lb_demo;

import org.springframework.boot.WebApplicationType;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.cloud.client.circuitbreaker.EnableCircuitBreaker;
import org.springframework.cloud.context.config.annotation.RefreshScope;

@SpringBootApplication
@EnableCircuitBreaker
@RefreshScope //to reload settings of configServer
public class EurekaLbDemoApplication {

	public static void main(String[] args) {
		new SpringApplicationBuilder(EurekaLbDemoApplication.class)
		.web(WebApplicationType.SERVLET)
		.run(args);
	}

}

