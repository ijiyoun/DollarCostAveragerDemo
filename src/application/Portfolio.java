package application;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;

/**
 * Holds all data about a portfolio. In a dollar cost average case, 
 * all single purchases (monthly) will be handled as separate assets 
 * (for example AMZN will be repeated multiple times with different purchaseDate/costBasis) 
 * @author yayan
 *
 */
public class Portfolio {

	private ArrayList<PortfolioAsset> portfolio;  
	private HashMap<Date, Double> cashFlow;
	private Double cashBalance;


	public Portfolio () {
		ArrayList<PortfolioAsset> portfolio = new ArrayList <PortfolioAsset> ();
		this.portfolio = portfolio;

		HashMap<Date, Double> cashFlow = new HashMap<Date, Double>();
		this.cashFlow = cashFlow;

		cashBalance = 0.0;
	}


	/**
	 * adds a new asset to the portfolio
	 * @param ticker
	 * @param investmentAmount
	 * @param price
	 */
	public void buyAssetByAmount (String ticker, Double investmentAmount, Double commission, Date purchaseDate, Double purchasePrice) {

		if (cashBalance >= investmentAmount + commission) {
			Double quantity = investmentAmount / purchasePrice;
			PortfolioAsset newAssetToBuy = new PortfolioAsset(ticker, quantity, purchaseDate, purchasePrice);

			portfolio.add(newAssetToBuy);
			withdrawCashBalance(investmentAmount + commission);
		}
	}


	/**
	 * Update value of all PortfolioAsset to a new value as per Date date and 
	 * calculate a total value
	 * @param date
	 * @return
	 */
	public Double updateAndReturnTotalValue (Date date, HistoricalData data) {

		Double total = cashBalance;

		for (PortfolioAsset asset : portfolio) {

			asset.updateCurrentPriceAndValue(data.pullClosestDataInstance(date).getValue());
			total = total + asset.getCurrentValue();

		}

		return total;
	}
	
	/**
	 * adds dividends
	 * @param date
	 * @param dividend
	 */
	public void addDividends (Date date, Double dividend, Double tax) {

		for (PortfolioAsset asset : portfolio) {

			Double dividendAfterTax =  dividend * (1 - tax) * asset.getQuantity();
			asset.addDividends(dividendAfterTax);
			addCashBalance(dividendAfterTax);

		}
	}
	/**
	 * returns total accumulated dividends
	 * @return
	 */
	public Double getAccumulatedDividends () {
		Double total = 0.0;
		
		for (PortfolioAsset asset : portfolio) {
			
			total = total + asset.getAccumulatedDividend();


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

	public Double getCashBalance() {
		return cashBalance;
	}

	/**
	 * add cash to the balance
	 * @param cash
	 */
	public void addCashBalance(Double cash) {
		cashBalance = cashBalance + cash;
	}

	/**
	 * withdraw cash from the balance
	 * @param cash
	 * @return false if not enough cash
	 */

	public void withdrawCashBalance(Double cash) {

		cashBalance = cashBalance - cash;


	}

	/**
	 * check whether portfolio has enough cash
	 * @param cash
	 * @return
	 */
	public boolean cashAvailable (Double cash) {
		if (cashBalance - cash >= 0) {
			return true;
		} else {
			return false;
		}

	}


	/**
	 * returns total quantity of assets by ticker
	 * @param ticker
	 * @return
	 */

	public Double getTotalQuantityByTicker (String ticker) {

		Double total = 0.0;

		for (PortfolioAsset portfolioAsset : portfolio) {
			total = total + portfolioAsset.getQuantity();
		}
		return total;
	}


}
