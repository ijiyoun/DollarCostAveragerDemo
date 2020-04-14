package application;
import java.util.Date;

/**
 * One instance of a historical data (uses Double value for both dividends or prices)
 * @author yayan
 *
 */
public class HistoricalDataInstance implements Comparable<HistoricalDataInstance> {

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

	@Override
	public int compareTo(HistoricalDataInstance otherInstance) {
		HistoricalDataInstance myOtherInstance = (HistoricalDataInstance) otherInstance;
		if (myOtherInstance.getDate().equals(date)) return 0;
		if (myOtherInstance.getDate().before(date)) return 1;
		return -1;
	}



	
	
}
