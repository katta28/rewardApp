package com.retailer.rewardapp.util;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.stereotype.Service;

import com.retailer.rewardapp.dto.Customer;


@Service
public class SpreadSheetProcessor {

	/* Method to read the spreadsheet and add them to a Map */
	public Map<String, Object> readSpreadSheet() throws IOException, FileNotFoundException{
		HashMap<String, Object> map = new HashMap<>();

		try {
			ClassLoader classLoader = this.getClass().getClassLoader();
			InputStream inputStream = classLoader.getResourceAsStream("input/RetailRewardLog.xlsx");

			XSSFWorkbook  workbook = new XSSFWorkbook(inputStream);
			if (workbook != null) {
				XSSFSheet sheet1 = workbook.getSheetAt(0);
				if (sheet1 != null) {
					System.out.println("Rows in sheet=="+sheet1.getLastRowNum());
					for (int i=1; i <= sheet1.getLastRowNum(); i++ ) {
						Row row = sheet1.getRow(i);
						Customer customer = new Customer();

						Cell cell1 = row.getCell(0);
						if (cell1 != null) {
							Double coustomerID = cell1.getNumericCellValue();
							customer.setCustomerId(coustomerID.intValue());
						}
						Cell cell2 = row.getCell(1);
						if (cell2 != null) {
							Date purchaseDate = cell2.getDateCellValue();
							customer.setDateOfPurchase(purchaseDate);
						}

						Cell cell3 = row.getCell(2);
						if (cell3 != null) {
							String item = cell3.getStringCellValue();
							customer.setItem(item);
						}

						Cell cell4 = row.getCell(3);
						if (cell1 != null) {
							double salePrice = cell4.getNumericCellValue();
							BigDecimal dec = BigDecimal.valueOf(salePrice);
							customer.setPrice(dec);
						}
						map.put("Row"+i, customer);
					}
				}
			}
		} catch (IOException e) {
			System.out.println(e.getMessage());
			throw e;
		}
		return map;
	}

	/* the values are taken from the map and logic is performed to get the aggregated points for a customer by month and total*/
	public Map calculateRewardPoints(HashMap<String, Object> map) {
		Map<Integer, Map<String,BigDecimal>>  rewardvaluesByIDMonthTot = new HashMap<>();
		Calendar cal = Calendar.getInstance();
		if (map != null) {
			List<Customer> customers = new ArrayList(map.values());
			Map<Integer, List<Customer>> valuesGroupedByID = customers.stream().collect(Collectors.groupingBy(
					Customer::getCustomerId));
			if (valuesGroupedByID != null) {
				for (Map.Entry<Integer,List<Customer>> entry : valuesGroupedByID.entrySet()) {
					List<Customer> customerList = valuesGroupedByID.get(entry.getKey());
					Map<String,BigDecimal> monthTotMap = new LinkedHashMap<>();
					for (Customer cus : customerList) {
						if (cus.getDateOfPurchase() != null) {
							String  month = new SimpleDateFormat("MMMM").format(cus.getDateOfPurchase());
							BigDecimal rewardpoint50 = BigDecimal.ZERO;
							BigDecimal rewardpoint100 = BigDecimal.ZERO;
							if (cus.getPrice().compareTo(new BigDecimal("50")) == 1) {
								rewardpoint50 = cus.getPrice().subtract(new BigDecimal("50")).multiply(new BigDecimal("1"));
							}
							if (cus.getPrice().compareTo(new BigDecimal("100")) == 1) {

								rewardpoint100 = cus.getPrice().subtract(new BigDecimal("100")).multiply(new BigDecimal("1"));
							}
							BigDecimal rewardpoint = rewardpoint50.add(rewardpoint100);

							if (monthTotMap.containsKey(month)) {
								BigDecimal aggRewardpoint = monthTotMap.get(month).add(rewardpoint);
								monthTotMap.put(month, aggRewardpoint);
							} else {
								monthTotMap.put(month, rewardpoint);
							}
						}
					}
					if (monthTotMap != null && monthTotMap.size()>0) {
						List<BigDecimal> monthlyValuesList = new ArrayList(monthTotMap.values());
						BigDecimal total = monthlyValuesList.stream()
								.reduce(BigDecimal.ZERO,BigDecimal::add);
						monthTotMap.put("Total", total);
					}
					rewardvaluesByIDMonthTot.put(entry.getKey(), monthTotMap);				
				}
			}
		}
		return rewardvaluesByIDMonthTot;
	}
}
