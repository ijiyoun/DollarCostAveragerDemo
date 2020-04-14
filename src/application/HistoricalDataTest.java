package application;
import static org.junit.jupiter.api.Assertions.*;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.junit.jupiter.api.Test;

class HistoricalDataTest {

/**
 * runs test based on s&p csv
 * @param date in format "1872-02-01"
 * @return
 */
	public Double runTest (String date) {
		
		HistoricalData priceData = new HistoricalData();
		priceData = ReaderCSV.readFromCSV("sp500_price_test.csv");
		priceData = priceData.filterByTicker("S&P 500");
		return priceData.pullClosestDataInstance(Util.parseDate(date)).getValue();

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
	
	@Test
	void testFilteringFunction () {
		HistoricalData data = ReaderCSV.readFromCSV("sp500_price_test.csv");
		HistoricalData filtered = data.filterByTicker("S&P 500");
		assertEquals(1764.0, filtered.getData().size());
	}
	
	@Test
	void testFXdata() {
		HistoricalData data = ReaderCSV.readFromCSV("usdjpy.csv");
		HistoricalData filtered = data.filterByTicker("USDJPY");
		Double result = filtered.pullClosestDataInstance(Util.parseDate("2019-10-11")).getValue();
		assertEquals(108.52, result);	
	}
}
