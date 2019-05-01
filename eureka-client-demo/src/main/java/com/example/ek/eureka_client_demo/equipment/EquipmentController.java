package com.example.ek.eureka_client_demo.equipment;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import org.apache.commons.collections.CollectionUtils;
import org.lognet.springboot.grpc.GRpcService;
import org.springframework.stereotype.Controller;

import com.example.ek.eureka_client_demo.equipment.EquipmentServiceGrpc.EquipmentServiceImplBase;

import io.grpc.stub.StreamObserver;

/**
 * eureka-client-demo
 * com.example.ek.eureka_client_demo.equipment.EquipmentController.java
 *
 * 学校にある備品情報を返却するクラス
 * プロトコルはgRPC
 *
 * @author E-Kanegae
 *
 */
@GRpcService
@Controller
public class EquipmentController extends EquipmentServiceImplBase {

	private static Map<String, List<Equipment>> schoolEquipmentDB;

	@Override
	public void getEquipment(EquipmentRequest req, StreamObserver<EquipmentResponse> resObserber) {
		String schoolNm = req.getSchoolName();
		List<Equipment> list = schoolEquipmentDB.get(schoolNm);

		EquipmentResponse equipmentResponsse = null;
		if (CollectionUtils.isEmpty(list)) {
			equipmentResponsse = EquipmentResponse.newBuilder()
					.setMessage(
							"There is no equipment in this scool " + schoolNm + System.getProperty("line.separator"))
					.build();
			resObserber.onNext(equipmentResponsse);
		} else {
			EquipmentResponse.Builder responseBuilder = EquipmentResponse.newBuilder();
			for (Equipment eq : list) {
				EquipmentInfo eqInfo = EquipmentInfo.newBuilder()
						.setCatagory(eq.getCategory())
						.setName(eq.getName())
						.build();
				responseBuilder.addEquipmentInfo(eqInfo);
			}
			EquipmentResponse res = responseBuilder.setMessage("").build();
			resObserber.onNext(res);
		}
		resObserber.onCompleted();
	}

	static {
		schoolEquipmentDB = new HashMap<String, List<Equipment>>();

		List<Equipment> lst = new ArrayList<Equipment>();
		Equipment eqp = new Equipment("chair", "High Grade Chair I");
		lst.add(eqp);
		eqp = new Equipment("desk", "High Grade Desk I");
		lst.add(eqp);

		schoolEquipmentDB.put("abcschool", lst);

		lst = new ArrayList<Equipment>();
		eqp = new Equipment("chair", "Low Grade Chair I");
		lst.add(eqp);
		//		eqp = new Equipment("desk", "Low Grade Desk I");
		//		lst.add(eqp);

		schoolEquipmentDB.put("xyzschool", lst);

		// DBの中を確認
		System.out.println("School Equipment information are as below.");
		for (Entry<String, List<Equipment>> entry : schoolEquipmentDB.entrySet()) {
			System.out.println("School: " + entry.getKey());
			for (Equipment e : entry.getValue()) {
				System.out.println("    Equipment:" + e.getCategory() + "," + e.getName());
			}
		}

	}

}
