package com.retailer.rewardapp.controller;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.retailer.rewardapp.dto.RewardMonthTot;
import com.retailer.rewardapp.util.SpreadSheetProcessor;

@RestController
public class RewardsRestController {


	@Autowired
	SpreadSheetProcessor processor;


	@GetMapping(value = "/fetchRewardResults")
	public @ResponseBody List<RewardMonthTot> getRewardResults()throws Exception {
		
		System.out.println("Entered the get method");
		Map<Integer, Map<String,BigDecimal>>  rewardvaluesByIDMonthTot = new HashMap<>();
		List<RewardMonthTot> customerRewardsList = new ArrayList<>();
		try {
			HashMap<String, Object> map = (HashMap<String,Object>)processor.readSpreadSheet();
			if (map !=null) {
				rewardvaluesByIDMonthTot = processor.calculateRewardPoints(map);
				for (Map.Entry<Integer, Map<String,BigDecimal>> entry : rewardvaluesByIDMonthTot.entrySet()) {
					RewardMonthTot rewardMonthTot = new RewardMonthTot();
					rewardMonthTot.setCustomerId(entry.getKey().toString());
					rewardMonthTot.setMonthAndTotalMap(rewardvaluesByIDMonthTot.get(entry.getKey()));
					customerRewardsList.add(rewardMonthTot);
				}
			}

		} catch(Exception ex) {
			ResourseNotFound rex = new ResourseNotFound("Resource Not found");
		}
		System.out.println("Before returing results");
		return customerRewardsList;
	}
	@GetMapping(value = "/invokeException")
	public @ResponseBody Object invokeException()throws Exception {
		
		System.out.println("Custom exception");
		try {
			 throw new RuntimeException();

		} catch(RuntimeException ex) {
			ResourseNotFound rex = new ResourseNotFound("Check your Url");
			return rex.getMessage();
		}
	}
}

