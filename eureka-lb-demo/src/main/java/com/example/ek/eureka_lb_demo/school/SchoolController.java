package com.example.ek.eureka_lb_demo.school;

import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.context.annotation.Bean;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import com.example.ek.eureka_client_demo.equipment.EquipmentInfo;
import com.example.ek.eureka_client_demo.equipment.EquipmentRequest;
import com.example.ek.eureka_client_demo.equipment.EquipmentResponse;
import com.example.ek.eureka_client_demo.equipment.EquipmentServiceGrpc;
import com.example.ek.eureka_client_demo.equipment.EquipmentServiceGrpc.EquipmentServiceBlockingStub;
import com.netflix.appinfo.InstanceInfo;
import com.netflix.discovery.EurekaClient;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixProperty;

import io.grpc.ManagedChannel;
import io.grpc.ManagedChannelBuilder;

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

	@Autowired
	EurekaClient client;

	@Bean
	@LoadBalanced
	public RestTemplate restTemplate() {
		return new RestTemplate();
	}

	/**
	 * <pre>
	 * school nameを元にeureka-client-demo APから生徒情報を取得するメソッド
	 *
	 * </pre>
	 * @param schoolname
	 * @return
	 */
	@GetMapping(value = "/getSchoolDetails/{schoolname}")
	@HystrixCommand(fallbackMethod = "getStudentInfoFallback", commandProperties = {
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

		return "School Name -  " + schoolname + " \n Student Information - " + response
				+ System.getProperty("line.separator");
	}

	/**
	 *
	 * Fallback用メソッド
	 *
	 */
	@SuppressWarnings("unused")
	private String getStudentInfoFallback(String schoolname) {

		System.out
				.println("Student Service is down!!! fallback route enabled..." + System.getProperty("line.separator"));

		return "CIRCUIT BREAKER ENABLED!!! No Response From Student Service at this moment. " +
				" Service will be back shortly - " + new Date() + System.getProperty("line.separator");
	}

	/**
	 * <pre>
	 * school Nameをパラメータとして備品情報をeureka-client-demoから取得して返却するメソッド
	 * eureka-client-demoとの通信プロトコルはREST APIではなく、gRPCを使用。
	 * </pre>
	 * @param schoolname
	 * @return
	 */
	@GetMapping(value = "/getEquipmentInfo/{schoolname}")
	public String getEquipments(@PathVariable String schoolname) {
		System.out.println("Getting School details for " + schoolname);

		InstanceInfo instance = client.getNextServerFromEureka("studentService", false);
		ManagedChannel channel = ManagedChannelBuilder.forAddress(instance.getIPAddr()
		//				, instance.getPort()) // EurekaをONにする場合はこっちでも大丈夫だが、今はOFFのため。
				, 6565)
				.usePlaintext().build();
		EquipmentServiceBlockingStub stb = EquipmentServiceGrpc.newBlockingStub(channel);
		EquipmentRequest req = EquipmentRequest.newBuilder().setSchoolName(schoolname).build();

		EquipmentResponse res = stb.getEquipment(req);

		if (StringUtils.hasLength(res.getMessage())) {
			return res.getMessage();
		}

		StringBuilder sb = new StringBuilder();
		for (EquipmentInfo eq : res.getEquipmentInfoList()) {
			sb.append(editResponseForEquipment(eq));
			sb.append(System.getProperty("line.separator"));
		}

		return sb.toString();
	}

	private String editResponseForEquipment(EquipmentInfo eq) {
		return "Category: " + eq.getCatagory() + ", " + "EquipmentName: " + eq.getName() + ";";
	}

}
