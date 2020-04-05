import java.util.Date;

/**
 * One instance of a historical data (uses Double value for both dividends or prices)
 * @author yayan
 *
 */
public class HistoricalDataInstance {

	private String ticker;
	private Date date;
	private Double value;
	
	public HistoricalDataInstance (String ticker, Date date, Double value) {
		this.ticker = ticker;
		this.date = date;
		this.value = value; 		
		
	}

	/**
	 * get ticker
	 * @return
	 */
	public String getTicker() {
		return ticker;
	}

	/**
	 * get date
	 * @return
	 */
	public Date getDate() {
		return date;
	}

	/**
	 * get value
	 * @return
	 */
	public Double getValue() {
		return value;
	}



	
	
}
