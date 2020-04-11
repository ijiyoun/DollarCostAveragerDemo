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
	private HashMap<Date, Double> dividendPendingReceival;
	private Double cashBalance;


	public Portfolio () {
		ArrayList<PortfolioAsset> portfolio = new ArrayList <PortfolioAsset> ();
		this.portfolio = portfolio;

		HashMap<Date, Double> cashFlow = new HashMap<Date, Double>();
		this.cashFlow = cashFlow;

		HashMap<Date, Double> dividendPendingReceival = new HashMap<Date, Double>();
		this.dividendPendingReceival = dividendPendingReceival;
		
		cashBalance = 0.0;
	}


	/**
	 * adds a new asset to the portfolio
	 * @param ticker
	 * @param investmentAmount
	 * @param price
	 */
	public void buyAssetByAmount (String ticker, Double investmentAmount, Double commission, Date purchaseDate, Double purchasePrice) {

		if (cashBalance >= investmentAmount) {
			Double quantity = (investmentAmount - commission) / purchasePrice;
			PortfolioAsset newAssetToBuy = new PortfolioAsset(ticker, quantity, purchaseDate, purchasePrice);

			portfolio.add(newAssetToBuy);
			withdrawCashBalance(investmentAmount);
		}
	}


	/**
	 * Update value of all PortfolioAsset to a new value as per Date date and 
	 * calculate a total value
	 * @param date
	 * @return
	 */
	public Double updateAndReturnTotalValue (Date date, HistoricalData priceData) {

		Double total = cashBalance;

		for (PortfolioAsset asset : portfolio) {

			asset.updateCurrentPriceAndValue(priceData.pullClosestDataInstance(date).getValue());
			total = total + asset.getCurrentValue();

		}

		return total;
	}
	
	/**
	 * adds dividends
	 * @param date
	 * @param dividend
	 */
	public void addDividendsToPendingReceival (Date calcDate, HistoricalData dividendData, Double tax) {

		for (PortfolioAsset asset : portfolio) {
			Double dividendDistributed = dividendData.pullClosestDataInstance(calcDate).getValue();
			Double dividendAfterTax =  dividendDistributed * (1 - tax) * asset.getQuantity();
			asset.addDividends(dividendAfterTax); //adding now even if pending receival
			
			int daysBetweenCalcAndDividendReceival = 1;
			Date dividendReceivalDate = Util.calcDate(calcDate, daysBetweenCalcAndDividendReceival); 
			
			if (dividendPendingReceival.containsKey(dividendReceivalDate)) {
				Double currentValue = dividendPendingReceival.get(dividendReceivalDate);
				dividendPendingReceival.put(dividendReceivalDate, currentValue + dividendAfterTax);
			//	System.out.println(" dividends triggered " + dividendReceivalDate + " value " + dividendAfterTax);
			} else {
				dividendPendingReceival.put(dividendReceivalDate, dividendAfterTax);
			}
			
			

		}
	}
	
	public void cashDividendsPendingReceival (Date date) {
		for (Date thisDate: dividendPendingReceival.keySet()) {
			if (date.equals(thisDate)) {
				addCashBalance(dividendPendingReceival.get(thisDate));
				dividendPendingReceival.remove(thisDate);
			}
			
		}
		
	}
	
	public Double getDividendPendingReceivalTotal () {
		Double total = 0.0;
		for (Date currentDate : dividendPendingReceival.keySet()) {
			total = total + dividendPendingReceival.get(currentDate);
			
		}
		
		return total;
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
	
	
	public ArrayList<PortfolioAsset> getMainArray() {
		return portfolio;
	}


}
