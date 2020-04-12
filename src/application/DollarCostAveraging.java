package application;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * DollarCostAveraging main class
 * @author yayan
 *
 */

public class DollarCostAveraging {
	
	
	String ticker;
	String investmentCurrency;
	String transactionCurrency;
	String startDateString;
	String endDateString;
	Double additionalInvestment;
	Double transactionCost;
	Double taxRate;
	
	public static HashMap<Date, Double>calcResult;
	public static HashMap<Date, Double>cashFlow;

	/**
	 * runs dollar cost averaging calculation
	 */
	void run (String ticker, String investmentCurrency, String transactionCurrency, String startDates, String endDates,
			Double investment, Double transactionCost, Double taxRate) {
		
		
		//enter values for calculation. 
		this.ticker =ticker;
		this.investmentCurrency = investmentCurrency;
		this.transactionCurrency = transactionCurrency;
		startDateString = startDates;
		endDateString = endDates;
		additionalInvestment = investment;
		this.transactionCost = transactionCost;
		this.taxRate = taxRate;
		

		//read price data
		HistoricalData priceData = new HistoricalData();
		priceData = ReaderCSV.readFromCSV("sp500_monthly_data_csv.csv");
		
		//read dividend data
		HistoricalData dividendData = new HistoricalData();
		dividendData = ReaderCSV.readFromCSV("sp500_dividends.csv");
		
		
		//read FX data
		//code to be added 
		
		
		System.out.println(ticker + investmentCurrency + transactionCurrency + startDates + endDates + investment + transactionCost + taxRate);
		int numDaysBetweenInvestment = 30;
		boolean reinvest = false;
		
		
		//COMMENT: is it better to have a separate method to return the values? 
		Portfolio myPortfolio = new Portfolio();
		Calculation myCalc = new Calculation(myPortfolio, priceData, dividendData, null);
		
		//HashMap<Date, Double> calcResult = new HashMap<Date, Double>();
		calcResult = new HashMap<Date, Double>();
		calcResult = myCalc.calculateDollarCostAveraging(startDateString, endDateString, additionalInvestment, transactionCurrency, numDaysBetweenInvestment, reinvest, transactionCost, taxRate);;
		
		//to get the cash flow data
		cashFlow = myCalc.getPortfolio().getCashFlow();
	
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
