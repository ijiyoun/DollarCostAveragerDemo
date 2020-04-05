package application;
import java.util.Date;

/**
 * Holds information about a single asset in Portfolio
 * @author yayan
 *
 */
public class PortfolioAsset {

	private String ticker;
	private Double quantity;
	private String currency;
	private Date purchaseDate;
	private Double purchasePrice;
	private Double purchaseValue;
	private Double currentPrice;
	private Double currentValue;

	
	public PortfolioAsset (String ticker, Double quantity, Date purchaseDate, Double purchasePrice) {
		this.ticker = ticker;
		this.quantity = quantity;
		this.purchaseDate = purchaseDate;
		this.purchasePrice = purchasePrice;
		purchaseValue = purchasePrice * quantity;
		//assigning current price and value to ZERO - this will be changed when updateCurrentPriceAndValue method is called
		currentPrice = 0.0;
		currentValue = 0.0;
		
		
	}
	
	/**
	 * Updates price/value to the latest as per input data
	 * @param Double newPrice 
	 * 
	 */
	public void updateCurrentPriceAndValue (Double newPrice) {
		
		currentPrice = newPrice;
		currentValue = currentPrice * quantity;
		
		
	}

	/**
	 * get ticker
	 * @return
	 */
	public String getTicker() {
		return ticker;
	}

	/**
	 * get quantity
	 * @return
	 */
	public Double getQuantity() {
		return quantity;
	}

	/**
	 * get currency
	 * @return
	 */
	public String getCurrency() {
		return currency;
	}

	/**
	 * get purchase date
	 * @return
	 */
	public Date getPurchaseDate() {
		return purchaseDate;
	}

	/**
	 * get purchase price
	 * @return
	 */
	public Double getPurchasePrice() {
		return purchasePrice;
	}

	/**
	 * get purchase value
	 * @return
	 */
	public Double getPurchaseValue() {
		return purchaseValue;
	}

	/**
	 * get current price
	 * @return
	 */
	public Double getCurrentPrice() {
		return currentPrice;
	}

	/**
	 * get current value 
	 * @return
	 */
	public Double getCurrentValue() {
		return currentValue;
	}
	
}
