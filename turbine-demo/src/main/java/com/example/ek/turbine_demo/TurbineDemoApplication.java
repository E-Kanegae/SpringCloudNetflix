package com.example.ek.turbine_demo;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.cloud.netflix.turbine.EnableTurbine;

@EnableTurbine
@SpringBootApplication
@RefreshScope //to reload settings of configServer
public class TurbineDemoApplication {

	public static void main(String[] args) {
		SpringApplication.run(TurbineDemoApplication.class, args);
	}

}

