package application;
import static org.junit.jupiter.api.Assertions.*;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.junit.jupiter.api.Test;

class HistoricalDataTest {

/**
 * runs test based on s&p csv
 * @param test in format "1872-02-01"
 * @return
 */
	public Double runTest (String test) {
		
		HistoricalData priceData = new HistoricalData();
		priceData = ReaderCSV.readFromCSV("sp500_monthly_data_csv.csv");
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		
		Date date = new Date();
		try {
			date = sdf.parse(test);
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		System.out.println(priceData.pullClosestDataInstance(date).getValue());
		
		return priceData.pullClosestDataInstance(date).getValue();

	}


	@Test
	void testPullClosestDataInstance1() {
		assertEquals(4.44, runTest("1871-01-01"));
	}
	
	@Test
	void testPullClosestDataInstance2() {
		assertEquals(5.07, runTest("1872-12-01"));
	}

	@Test
	void testPullClosestDataInstance3() {
		assertEquals(2664.34, runTest("2017-12-01"));
	}
	
	@Test
	void testPullClosestDataInstance4() {
		assertEquals(2170.95, runTest("2016-08-15"));
	}
}
