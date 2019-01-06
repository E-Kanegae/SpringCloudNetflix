package com.example.ek.eureka_lb_demo.hello;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * eureka-lb-demo
 * com.example.ek.eureka_lb_demo.hello.HelloController.java
 *
 * @author etsukanegae
 *
 */
@RestController
public class HelloController {

	@RequestMapping("/")
	public String hello() {
		return "Hello World";
	}

}
