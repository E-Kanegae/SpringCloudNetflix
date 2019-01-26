package com.example.ek.hystrix_dashboard_demo;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.cloud.netflix.hystrix.dashboard.EnableHystrixDashboard;

@EnableHystrixDashboard
@SpringBootApplication
@RefreshScope //to reload settings of configServer
public class HystrixDashboardDemoApplication {

	public static void main(String[] args) {
		SpringApplication.run(HystrixDashboardDemoApplication.class, args);
	}

}

