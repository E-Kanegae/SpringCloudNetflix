package com.example.ek.eureka_client_demo.equipment;

import java.io.Serializable;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

/**
 * eureka-client-demo
 * com.example.ek.eureka_client_demo.equipment.Equipment.java
 *
 * 学校の備品名クラス
 *
 * @author E-Kanegae
 *
 */
@AllArgsConstructor
@Getter
@Setter
public class Equipment implements Serializable{

	/**
	 * 備品カテゴリ
	 */
	private String category;

	/**
	 * 備品名
	 */
	private String name;

}
