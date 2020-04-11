package application;
import static org.junit.jupiter.api.Assertions.*;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;

import org.junit.jupiter.api.Test;

class CalculationTest {

	Calculation myCalc;
	HashMap<Date, Double> calcResult;
	Portfolio myPortfolio;

	//values for tests
	String startDateString;
	String endDateString;
	Double additionalInvestment;
	int numDaysBetweenInvestment;
	String currency;
	Boolean reinvest;
	Double commission;
	Double dividendTax;

	public CalculationTest() {
		HistoricalData priceData = new HistoricalData();
		priceData = ReaderCSV.readFromCSV("sp500_monthly_data_csv.csv");

		HistoricalData dividendData = new HistoricalData();
		dividendData = ReaderCSV.readFromCSV("sp500_dividends.csv");

		Portfolio myPortfolio = new Portfolio();
		this.myPortfolio = myPortfolio;

		Calculation myCalc = new Calculation(this.myPortfolio, priceData, dividendData, null);
		this.myCalc = myCalc;

		HashMap<Date, Double> calcResult = new HashMap<Date, Double>();
		this.calcResult = calcResult;
	}

	public void executeTest( ) {
		calcResult = myCalc.calculateDollarCostAveraging (startDateString, endDateString, additionalInvestment, currency, numDaysBetweenInvestment, reinvest, commission, dividendTax);
	}

	
	@Test
	/**
	 * test for larger span without dividends
	 * 
	 */
	void test0() {

		startDateString = "2016-05-01";
		endDateString = "2017-02-05";
		additionalInvestment = 3000.0;
		numDaysBetweenInvestment = 31;
		currency = "USD";
		reinvest = false;
		commission = 0.0;
		dividendTax = 0.0;

		executeTest();

		Date testDate1 = Util.parseDate("2017-01-02");
		Date testDate2 = Util.parseDate("2017-02-05");

		assertEquals(13.78746524624310, myPortfolio.getTotalQuantityByTicker("total"), 0.00001); //this doesnt include Feb investement scheduled for 2/4
		assertEquals(27747.747921, calcResult.get(testDate1), 0.00001);
		assertEquals(35009.483978, calcResult.get(testDate2), 0.00001);
		//check that size is correct (meaning that scope is strictly limited to FROM startDate TO endDate)
		assertEquals(281, calcResult.size());
	}

	void test0withDividendTax() {

		startDateString = "2016-05-01";
		endDateString = "2017-02-05";
		additionalInvestment = 3000.0;
		numDaysBetweenInvestment = 31;
		currency = "USD";
		reinvest = false;
		commission = 0.0;
		dividendTax = 0.0;

		executeTest();

		Date testDate1 = Util.parseDate("2017-01-02");
		Date testDate2 = Util.parseDate("2017-02-05");

		assertEquals(13.78746524624310, myPortfolio.getTotalQuantityByTicker("total"), 0.00001); //this doesnt include Feb investement scheduled for 2/4
		assertEquals(32412.146234, calcResult.get(testDate2), 0.00001);
		//check that size is correct (meaning that scope is strictly limited to FROM startDate TO endDate)
		assertEquals(281, calcResult.size());
	}
	
	@Test
	/**
	 * test for 5 month investment without dividends
	 */
	void testxxx() {

		startDateString = "2017-01-01";
		endDateString = "2017-05-30";
		additionalInvestment = 1000.0;
		numDaysBetweenInvestment = 31;
		currency = "USD";
		reinvest = false;
		commission = 0.0;
		dividendTax = 0.0;

		executeTest();

		Date testDate1 = Util.parseDate("2017-05-15");
		Date testDate2 = Util.parseDate("2017-05-05");

		assertEquals(Math.round(5309.595311), Math.round(calcResult.get(testDate1)));
		assertEquals(Math.round(5309.595311), Math.round(calcResult.get(testDate2)));

	}

	


	@Test
	/**
	 * test for 5 month investment without dividends
	 */
	void WithDividends() {

		startDateString = "2017-01-01";
		endDateString = "2017-05-30";
		additionalInvestment = 1000.0;
		numDaysBetweenInvestment = 31;
		currency = "USD";
		reinvest = true;
		commission = 0.0;
		dividendTax = 0.0;		

		executeTest();

		Date testDate1 = Util.parseDate("2017-05-05");

		assertEquals(Math.round(5315.645241), Math.round(calcResult.get(testDate1)));


	}

	@Test
	/**
	 * test for 5 month investment with dividends tax and commission
	 */
	void test2WithReinvestmentDividendsTaxAndCommission() {

		startDateString = "2016-05-01";
		endDateString = "2016-07-02";
		additionalInvestment = 15000.0;
		numDaysBetweenInvestment = 31;
		currency = "USD";
		reinvest = true;
		commission = 1.5;
		dividendTax = 0.1;

		executeTest();

		Date testDate1 = Util.parseDate("2016-07-02");
		Date testDate2 = Util.parseDate("2016-07-01");
		Date testDate3 = Util.parseDate("2016-05-01");
		Date testDate4 = Util.parseDate("2016-06-15");

		assertEquals(46953.2895006988, calcResult.get(testDate1), 0.00001); 
		assertEquals(31368.1959165823, calcResult.get(testDate2), 0.00001);
		assertEquals(14998.5000000000, calcResult.get(testDate3), 0.00001);
		assertEquals(30419.22369, calcResult.get(testDate4), 0.00001);

		//check that stocks were bought only 4 times
		assertEquals(4, myPortfolio.getMainArray().size());

	}



	@Test
	/**
	 * test for 5 month investment without dividends
	 */
	void testShortWithDividends() {

		startDateString = "2017-01-01";
		endDateString = "2017-02-03";
		additionalInvestment = 1000.0;
		numDaysBetweenInvestment = 31;
		currency = "USD";
		reinvest = false;
		commission = 0.0;
		dividendTax = 0.0;

		executeTest();
		Date testDate1 = Util.parseDate("2017-02-03");
		assertEquals(2044.366891, calcResult.get(testDate1), 0.00001);


	}
	
	
	
	@Test

	void testThatStocksArePurchasedOnBothStartAndEndDateInclusive() {

		startDateString = "2017-01-01";
		endDateString = "2017-02-01";
		additionalInvestment = 1000.0;
		numDaysBetweenInvestment = 31;
		currency = "USD";
		reinvest = false;
		commission = 0.0;
		dividendTax = 0.0;

		executeTest();
		assertEquals(0.868738383, myPortfolio.getTotalQuantityByTicker("test"), 0.0001);


	}
}
