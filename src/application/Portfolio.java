package application;
import java.util.ArrayList;
import java.util.Date;

/**
 * Holds all data about a portfolio. In a dollar cost average case, 
 * all single purchases (monthly) will be handled as separate assets 
 * (for example AMZN will be repeated multiple times with different purchaseDate/costBasis) 
 * @author yayan
 *
 */
public class Portfolio {

	ArrayList<PortfolioAsset> portfolio = new ArrayList <PortfolioAsset> ();
	Double cashBalance;
	
	

/**
 * adds a new asset to the portfolio
 * @param ticker
 * @param investmentAmount
 * @param price
 */
	public void buyAssetByAmount (String ticker, Double investmentAmount, Date purchaseDate, Double purchasePrice) {
		
		Double quantity = investmentAmount / purchasePrice;
		PortfolioAsset newAssetToBuy = new PortfolioAsset(ticker, quantity, purchaseDate, purchasePrice);
		
		portfolio.add(newAssetToBuy);
		
	}
		
	
	/**
	 * Update value of all PortfolioAsset to a new value as per Date date and 
	 * calculate a total value
	 * @param date
	 * @return
	 */
	public Double updateAndReturnTotalValue (Date date, HistoricalData data) {
		
		Double total = 0.0;
		
		for (PortfolioAsset asset : portfolio) {
			
			asset.updateCurrentPriceAndValue(data.pullClosestDataInstance(date).getValue());
			total = total + asset.getCurrentValue();
			
		}
		
		return total;
	}
	/**
	 * Returns total dividend return on a given day
	 * @param date
	 * @param data (dividend data)
	 * @return
	 */
	public Double calculateDividends (Date date, HistoricalData data) {
		
		
		return null;
	}
	
	
}
