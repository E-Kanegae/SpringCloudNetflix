package com.example.ek.eureka_client_demo.hello;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * eureka-client-demo
 * com.example.ek.eureka_client_demo.hello.HelloController.java
 *
 * @author etsukanegae
 *
 */
@RestController
public class HelloController {

	@RequestMapping("/")
	public String home() {
		return "Hello World";
	}

}
