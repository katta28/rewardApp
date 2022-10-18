package com.retailer.rewardapp;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.retailer.rewardapp.util.SpreadSheetProcessor;

@SpringBootTest
class RewardappApplicationTests {

	@Autowired
	SpreadSheetProcessor processor;

	@Test
	void contextLoads() {
		try {
			HashMap<String, Object> map = (HashMap<String,Object>)processor.readSpreadSheet();
			assert map.size() >0 ;

			Map<Integer, Map<String,BigDecimal>>  rewardvaluesByIDMonthTot = processor.calculateRewardPoints(map);
			if (rewardvaluesByIDMonthTot  != null) {
				Map<String,BigDecimal> rewardsMap = rewardvaluesByIDMonthTot.get(Integer.valueOf("1"));

				assert rewardsMap.size() > 0;

				if (rewardsMap.containsKey("October")) {
					assert rewardsMap.get("October").compareTo(new BigDecimal("20")) == 0;
				}
			}
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

}
