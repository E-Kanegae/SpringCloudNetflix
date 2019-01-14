package com.example.ek.eureka_client_demo.student;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

/**
 * eureka-client-demo
 * com.example.ek.eureka_client_demo.student.StudentController.java
 *
 * TODO ここにクラスの説明を書いてください。
 *
 * @author etsukanegae
 *
 */
@RestController
public class StudentController {

	private static Map<String, List<Student>> schooDB;

	static {
		schooDB = new HashMap<String, List<Student>>();

		List<Student> lst = new ArrayList<Student>();
		Student std = new Student("Sajal", "Class IV");
		lst.add(std);
		std = new Student("Lokesh", "Class V");
		lst.add(std);

		schooDB.put("abcschool", lst);

		lst = new ArrayList<Student>();
		std = new Student("Kajal", "Class III");
		lst.add(std);
		std = new Student("Sukesh", "Class VI");
		lst.add(std);

		schooDB.put("xyzschool", lst);

		// DBの中を確認
		System.out.println("School information are as below.");
		for (Entry<String, List<Student>> entry : schooDB.entrySet()) {
			System.out.println("School: " + entry.getKey());
			for (Student s : entry.getValue()) {
				System.out.println("    Student:" + s.getName() + "," + s.getClassName());
			}
		}

	}

	@GetMapping(value = "/getStudentDetails/{schoolname}")
	public List<Student> getStudents(@PathVariable String schoolname) {
		System.out.println("Getting Student details for " + schoolname);

		List<Student> studentList = schooDB.get(schoolname);
		if (studentList == null) {
			studentList = new ArrayList<Student>();
			Student std = new Student("Not Found", "N/A");
			studentList.add(std);
		}
		return studentList;
	}

}
