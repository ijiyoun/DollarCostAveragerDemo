package application;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

class PortfolioTest {

	Portfolio myPortfolio;
	HistoricalData priceData;
	HistoricalData dividendData;
	
	public PortfolioTest() {
		Portfolio myPortfolio = new Portfolio();
		this.myPortfolio = myPortfolio;
		
		HistoricalData priceData = new HistoricalData();
		priceData = ReaderCSV.readFromCSV("sp500_monthly_data_csv.csv");
		this.priceData = priceData;
		
		HistoricalData dividendData = new HistoricalData();
		dividendData = ReaderCSV.readFromCSV("sp500_dividends.csv");
		this.dividendData = dividendData;
	}
	
	/**
	 * test that buyAssetByAmount correctly engages with cashBalance
	 */
	
	@Test
	void testBuyAssetByAmount() {
		myPortfolio.addCashBalance(3000.0);
		myPortfolio.buyAssetByAmount("test", 2000.0, 500.0,Util.parseDate("2020-04-10") , 100.0);
		assertEquals(15, myPortfolio.getTotalQuantityByTicker("test"));
		assertEquals(1000.0, myPortfolio.getCashBalance());
		
		myPortfolio.addCashBalance(3000.0);
		myPortfolio.buyAssetByAmount("test", 2001.0, 1.0,Util.parseDate("2017-12-01") , 100.0);
		assertEquals(1999, myPortfolio.getCashBalance());
	}

	@Test
	void testUpdateAndReturnTotalValue() {
		myPortfolio.addCashBalance(3000.0);
		myPortfolio.buyAssetByAmount("test", 2001.0, 1.0,Util.parseDate("2017-12-01") , 100.0);
		assertEquals(999, myPortfolio.getCashBalance());
		
	}
/**
 * test addCashBalance, getCashBalance, withdrawCashBalance
 */
	
	@Test
	void cashBalanceOperations() {
		myPortfolio.addCashBalance(100.13);
		assertEquals(100.13, myPortfolio.getCashBalance());
		
		myPortfolio.withdrawCashBalance(10.0);
		assertEquals(90.13,  myPortfolio.getCashBalance());
	}
	
	/**
	 * test operations related to dividends
	 */

	@Test
	void testCashDividendsPendingReceivalandGetDividendPendingReceival() {
		myPortfolio.addCashBalance(5001.0);
		myPortfolio.buyAssetByAmount("test", 2000.0, 1.0, Util.parseDate("2016-01-01"), 1918.6);
		
		myPortfolio.addDividendsToPendingReceival(Util.parseDate("2016-02-01"), dividendData, 0.0);
		
		assertEquals(3001.0, myPortfolio.getCashBalance()); //since dividends are not received yet
		assertEquals(45.55211091, myPortfolio.getAccumulatedDividends(), 0.0001); //since added to accumulated even if not cashed out 
		assertEquals(45.55211091, myPortfolio.getDividendPendingReceivalTotal(), 0.0001);
		
		myPortfolio.cashDividendsPendingReceival(Util.parseDate("2016-02-02"));
		assertEquals(3046.552111, myPortfolio.getCashBalance(), 0.0001); //all dividends are cashed out and added to cash
		assertEquals(0.0, myPortfolio.getDividendPendingReceivalTotal());
		assertEquals(45.55211091, myPortfolio.getAccumulatedDividends(), 0.0001);
	
	
	}


}