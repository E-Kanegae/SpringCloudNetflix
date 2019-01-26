package com.example.ek.eureka_lb_demo.school;

import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.context.annotation.Bean;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixProperty;

/**
 * eureka-lb-demo
 * com.example.ek.eureka_lb_demo.school.SchoolController.java
 * s
 * @author etsukanegae
 *
 */
@RestController
public class SchoolController {

	@Autowired
	RestTemplate restTemplate;

	@Bean
	@LoadBalanced
	public RestTemplate restTemplate() {
		return new RestTemplate();
	}

	@GetMapping(value = "/getSchoolDetails/{schoolname}")
	@HystrixCommand(fallbackMethod = "getStudentInfoFallback",
					commandProperties = {
					@HystrixProperty(name = "execution.timeout.enabled", value = "true"), //default
					@HystrixProperty(name = "execution.isolation.thread.timeoutInMilliseconds", value = "5000"),
					@HystrixProperty(name = "metrics.rollingStats.timeInMilliseconds", value = "30000"),
					@HystrixProperty(name = "circuitBreaker.requestVolumeThreshold", value = "5")
					})
	public String getStudents(@PathVariable String schoolname) {
		System.out.println("Getting School details for " + schoolname);

		String response = restTemplate.exchange("http://studentService/getStudentDetails/{schoolname}", HttpMethod.GET,
				null, new ParameterizedTypeReference<String>() {
				}, schoolname).getBody();

		System.out.println("Response Received as " + response);

		return "School Name -  " + schoolname + " \n Student Information - " + response;
	}

	/**
	 *
	 * Fallbackコマンドを呼び出すためだけにわざと失敗するメソッド<br>
	 * （存在しないserverIDを指定しているので、Serverが全てダウンもしくは回線障害が起きていることを想定）
	 *
	 */
	@GetMapping(value = "/getSchoolInfo/{schoolname}")
	@HystrixCommand(fallbackMethod = "getStudentInfoFallback")
	public String getStudentInfo(@PathVariable String schoolname) {
		System.out.println("Getting School information for " + schoolname);

		String response = restTemplate
				.exchange("http://student-information/getStudentDetails/{schoolname}", HttpMethod.GET,
						null, new ParameterizedTypeReference<String>() {
						}, schoolname)
				.getBody();

		System.out.println("Response Received as " + response);

		return "School Name -  " + schoolname + " \n Student Information - " + response;
	}

	/**
	 *
	 * Fallback用メソッド
	 *
	 */
	@SuppressWarnings("unused")
	private String getStudentInfoFallback(String schoolname) {

		System.out.println("Student Service is down!!! fallback route enabled...");

		return "CIRCUIT BREAKER ENABLED!!! No Response From Student Service at this moment. " +
				" Service will be back shortly - " + new Date();
	}

}
