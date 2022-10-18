package com.retailer.rewardapp.dto;

import java.math.BigDecimal;
import java.util.Map;

public class RewardMonthTot {

	private String customerId;
	private Map<String,BigDecimal> monthAndTotalMap;
	public String getCustomerId() {
		return customerId;
	}
	public void setCustomerId(String customerId) {
		this.customerId = customerId;
	}
	public Map<String, BigDecimal> getMonthAndTotalMap() {
		return monthAndTotalMap;
	}
	public void setMonthAndTotalMap(Map<String, BigDecimal> monthAndTotalMap) {
		this.monthAndTotalMap = monthAndTotalMap;
	}
}
