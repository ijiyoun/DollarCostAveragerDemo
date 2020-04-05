import static org.junit.jupiter.api.Assertions.*;


import java.util.Date;
import java.util.HashMap;

import org.junit.jupiter.api.Test;

class CalculationTest {

	@Test
	/**
	 * test for 5 month investment without dividends
	 */
	void testCalculateDollarCostAveragingWithoutDividends() {

		HistoricalData priceData = new HistoricalData();
		priceData = ReaderCSV.readFromCSV("sp500_monthly_data_csv.csv");
		
		String startDateString = "2017-01-01";
		String endDateString = "2017-05-30";
		Double additionalInvestment = 1000.0;
		int numDaysBetweenInvestment = 31;
		
		Calculation myCalc = new Calculation();
		HashMap<Date, Double> calcResult = new HashMap<Date, Double>();
		
		calcResult = myCalc.calculateDollarCostAveraging(priceData, null, startDateString, endDateString, additionalInvestment, numDaysBetweenInvestment);
		
	
		Date testDate1 = Util.parseDate("2017-05-15");
		Date testDate2 = Util.parseDate("2017-05-05");
		
		
		assertEquals(Math.round(5108.262287), Math.round(calcResult.get(testDate1)));
		assertEquals(Math.round(5108.262287), Math.round(calcResult.get(testDate2)));
	
	}

	@Test
	/**
	 * test for larger span without dividends
	 * 
	 */
	void testCalculateDollarCostAveragingWithoutDividendsDailyPorfolioValue() {

		HistoricalData priceData = new HistoricalData();
		priceData = ReaderCSV.readFromCSV("sp500_monthly_data_csv.csv");
		
		String startDateString = "2016-05-01";
		String endDateString = "2017-02-01";
		Double additionalInvestment = 2100.0;
		int numDaysBetweenInvestment = 155;
		
		Calculation myCalc = new Calculation();
		HashMap<Date, Double> calcResult = new HashMap<Date, Double>();
		
		calcResult = myCalc.calculateDollarCostAveraging(priceData, null, startDateString, endDateString, additionalInvestment, numDaysBetweenInvestment);
		
	
		Date testDate1 = Util.parseDate("2017-01-01");
		Date testDate2 = Util.parseDate("2017-02-01");
	
		
		assertEquals(Math.round(4542.513447), Math.round(calcResult.get(testDate1)));
		assertEquals(Math.round(4651.907374), Math.round(calcResult.get(testDate2)));
		//check that size is correct (meaning that scope is strictly limited to FROM startDate TO endDate)
		assertEquals(277, calcResult.size());
	}
	
}
