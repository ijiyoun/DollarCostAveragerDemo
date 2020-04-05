import java.text.SimpleDateFormat;
import java.util.*;

/**
 * DollarCostAveraging main class
 * @author yayan
 *
 */

public class DollarCostAveraging {
	
	String currency;
	String startDateString;
	String endDatesString;
	String dividend;
	String transactionCost;
	String taxRate;

	/**
	 * runs dollar cost averaging calculation
	 */
	void run (String currency, String startDate, String endDate, String dividend, String transactionCost, String taxRate) {
		
		

		//read price data
		HistoricalData priceData = new HistoricalData();
		priceData = ReaderCSV.readFromCSV("sp500_monthly_data_csv.csv");
		
		//read dividend data
		//code to be added 
		
		
		//read FX data
		//code to be added 
		
		
		
		
		//entry values for calculation - will be changed to dynamic upon UI completion
		String startDateString = "2010-01-01";
		String endDateString = "2015-01-31";
		Double additionalInvestment = 1000.0;
		int numDaysBetweenInvestment = 30;
		
		
		Calculation myCalc = new Calculation();
		
		HashMap<Date, Double> calcResult = new HashMap<Date, Double>();
		calcResult = myCalc.calculateDollarCostAveraging(priceData, null, startDateString, endDateString, additionalInvestment, numDaysBetweenInvestment);
		
	
		//show results in order by date
		for (int i = 0; i < calcResult.size(); i++) {
			Calendar c = Calendar.getInstance();
			c.setTime(Util.parseDate(startDateString));
			c.add(Calendar.DATE, i); //number of days to add
			Date dateToPrint = c.getTime();
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
			System.out.println(sdf.format(dateToPrint) + " total value is " + calcResult.get(dateToPrint));
			
		}
		
		
		/**
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		for (Date date : calcResult.keySet()) {
			System.out.println(sdf.format(date) + " total value is " + calcResult.get(date));
			
		}
			**/
		
			
		
	}

}
