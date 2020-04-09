package application;
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
		
		
		HistoricalData dividendData = new HistoricalData();
		dividendData = ReaderCSV.readFromCSV("sp500_dividends.csv");
		
		String startDateString = "2017-01-01";
		String endDateString = "2017-05-30";
		Double additionalInvestment = 1000.0;
		int numDaysBetweenInvestment = 31;
		String currency = "USD";
		Boolean reinvest = false;
		Double commission = 0.0;
		Double dividendTax = 0.0;
		
		Calculation myCalc = new Calculation(priceData, dividendData, null);
		HashMap<Date, Double> calcResult = new HashMap<Date, Double>();
		
		calcResult = myCalc.calculateDollarCostAveraging (startDateString, endDateString, additionalInvestment, currency, numDaysBetweenInvestment, reinvest, commission, dividendTax);
		
	
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
		

		HistoricalData dividendData = new HistoricalData();
		dividendData = ReaderCSV.readFromCSV("sp500_dividends.csv");
		
		
		String startDateString = "2016-05-01";
		String endDateString = "2017-02-01";
		Double additionalInvestment = 2100.0;
		int numDaysBetweenInvestment = 155;
		String currency = "USD";
		Boolean reinvest = false;
		Double commission = 0.0;
		Double dividendTax = 0.0;
		
		Calculation myCalc = new Calculation(priceData, dividendData, null);
		HashMap<Date, Double> calcResult = new HashMap<Date, Double>();
		
		calcResult = myCalc.calculateDollarCostAveraging (startDateString, endDateString, additionalInvestment, currency, numDaysBetweenInvestment, reinvest, commission, dividendTax);
		
		
	
		Date testDate1 = Util.parseDate("2017-01-01");
		Date testDate2 = Util.parseDate("2017-02-01");
	
		
		assertEquals(Math.round(4542.513447), Math.round(calcResult.get(testDate1)));
		assertEquals(Math.round(4651.907374), Math.round(calcResult.get(testDate2)));
		//check that size is correct (meaning that scope is strictly limited to FROM startDate TO endDate)
		assertEquals(277, calcResult.size());
	}
	
	
	@Test
	/**
	 * test for 5 month investment without dividends
	 */
	void WithDividends() {

		HistoricalData priceData = new HistoricalData();
		priceData = ReaderCSV.readFromCSV("sp500_monthly_data_csv.csv");
		
		
		HistoricalData dividendData = new HistoricalData();
		dividendData = ReaderCSV.readFromCSV("sp500_dividends.csv");
		
		String startDateString = "2017-01-01";
		String endDateString = "2017-05-30";
		Double additionalInvestment = 1000.0;
		int numDaysBetweenInvestment = 31;
		String currency = "USD";
		Boolean reinvest = true;
		Double commission = 0.0;
		Double dividendTax = 0.0;
		
		Calculation myCalc = new Calculation(priceData, dividendData, null);
		HashMap<Date, Double> calcResult = new HashMap<Date, Double>();
		
		
	
		Date testDate1 = Util.parseDate("2017-05-15");
		Date testDate2 = Util.parseDate("2017-05-05");
		
		
		calcResult = myCalc.calculateDollarCostAveraging (startDateString, endDateString, additionalInvestment, currency, numDaysBetweenInvestment, reinvest, commission, dividendTax);
		assertEquals(Math.round(5315.645241), Math.round(calcResult.get(testDate2)));
		
	
	}
	
	@Test
	/**
	 * test for 5 month investment with dividends tax and commission
	 */
	void WithDividendsTaxAndCommission() {

		HistoricalData priceData = new HistoricalData();
		priceData = ReaderCSV.readFromCSV("sp500_monthly_data_csv.csv");
		
		
		HistoricalData dividendData = new HistoricalData();
		dividendData = ReaderCSV.readFromCSV("sp500_dividends.csv");
		
		String startDateString = "2017-01-01";
		String endDateString = "2017-05-30";
		Double additionalInvestment = 1000.0;
		int numDaysBetweenInvestment = 31;
		String currency = "USD";
		Boolean reinvest = true;
		Double commission = 1.0;
		Double dividendTax = 0.1;
		
		Calculation myCalc = new Calculation(priceData, dividendData, null);
		HashMap<Date, Double> calcResult = new HashMap<Date, Double>();
		
		
	
		Date testDate1 = Util.parseDate("2017-05-15");
		Date testDate2 = Util.parseDate("2017-05-05");
		
		
		calcResult = myCalc.calculateDollarCostAveraging (startDateString, endDateString, additionalInvestment, currency, numDaysBetweenInvestment, reinvest, commission, dividendTax);
		assertEquals(Math.round(5286.00012), Math.round(calcResult.get(testDate2)));
		
	
	}
}



