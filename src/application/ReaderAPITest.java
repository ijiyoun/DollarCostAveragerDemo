package application;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

class ReaderAPITest {


	@Test
	void testReadPriceFromAPI() {
		HistoricalData priceData = new HistoricalData();
		String ticker = "LK";
		String testDate = "2019-05-17";
		Double expected  = 20.38;
		
		priceData = ReaderAPI.readPriceFromAPI(ticker);
		
		priceData = priceData.filterByTicker(ticker);
		Double result =  priceData.pullClosestDataInstance(Util.parseDate(testDate)).getValue();
		assertEquals(expected, result);
	}

	@Test
	void testReadDividendFromAPI() {
		HistoricalData priceData = new HistoricalData();
		String ticker = "AAPL";
		String testDate = "2013-02-07";
		Double expected  = 0.38;
		
		priceData = ReaderAPI.readDividendFromAPI(ticker);
		
		priceData = priceData.filterByTicker(ticker);
		Double result =  priceData.pullClosestDataInstance(Util.parseDate(testDate)).getValue();
		assertEquals(expected, result);
	}

}
