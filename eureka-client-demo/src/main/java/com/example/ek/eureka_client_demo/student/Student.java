package com.example.ek.eureka_client_demo.student;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

/**
 * eureka-client-demo
 * com.example.ek.eureka_client_demo.student.Student.java
 *
 * Studentクラス
 *
 * @author etsukanegae
 *
 */
@AllArgsConstructor
@Getter
@Setter
public class Student {

	/**
	 * 生徒名
	 */
	private String name;

	/**
	 * 授業名
	 */
	private String className;
}
