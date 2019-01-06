package com.example.ek.eureka_client_demo.student;

/**
 * eureka-client-demo
 * com.example.ek.eureka_client_demo.student.Student.java
 *
 * Studentクラス
 *
 * @author etsukanegae
 *
 */
public class Student {

	private String name;
	private String className;

	/**
	 * コンストラクタ
	 * @param name
	 * @param className
	 */
	protected Student(String name, String className) {
		setName(name);
		setClassName(className);
	}

	/**
	 * @return name
	 */
	public String getName() {
		return name;
	}

	/**
	 * @param name セットする name
	 */
	public void setName(String name) {
		this.name = name;
	}

	/**
	 * @return className
	 */
	public String getClassName() {
		return className;
	}

	/**
	 * @param className セットする className
	 */
	public void setClassName(String className) {
		this.className = className;
	}

}
