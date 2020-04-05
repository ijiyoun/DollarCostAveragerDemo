package application;
import java.util.*;

/**
 * Class to perform DollarCostAveraging calculations 
 * @author yayan
 *
 */
public class Calculation {

	/**
	 * Main method that returns Date/Double HashMap based on DollarCostAveraging calculations
	 * @param priceData
	 * @param dividendData
	 * @param startDateString
	 * @param endDateString
	 * @param additionalInvestment
	 * @param numDaysBetweenInvestment
	 * @return
	 */
	public HashMap<Date, Double> calculateDollarCostAveraging (HistoricalData priceData, HistoricalData dividendData, String startDateString, String endDateString, Double additionalInvestment, int numDaysBetweenInvestment) {


		HashMap<Date, Double> calcResult = new HashMap<Date, Double>();

		//initialize portfolio
		Portfolio myPortfolio = new Portfolio();


		//set start/end date for Dollar Cost Averaging
		Date startDate = Util.parseDate(startDateString);
		Date endDate = Util.parseDate(endDateString);
		Date loopDate = startDate;

		


		while (loopDate.compareTo(endDate) <= 0) {


			//add code to exchange incremental dividends to USD if needed




			//invest 1000 USD and calculate total value
			myPortfolio.buyAssetByAmount("", additionalInvestment, loopDate, priceData.pullClosestDataInstance(loopDate).getValue());
			Double totalValue = myPortfolio.updateAndReturnTotalValue(loopDate, priceData);	




			//main output
			calcResult.put(loopDate, totalValue);




			//add code to add dividends (and re-invest them if needed) and apply taxes if needed






			//calculate date for the next investment
			Calendar c = Calendar.getInstance();
			c.setTime(loopDate);
			c.add(Calendar.DATE, numDaysBetweenInvestment); //number of days to add
			Date nextInvestmentDate = c.getTime();
			
			//add daily data on portfolio value before the next investment 
			while ((loopDate.compareTo(nextInvestmentDate) < 0) && (loopDate.compareTo(endDate) <= 0)) {
				
				totalValue = myPortfolio.updateAndReturnTotalValue(loopDate, priceData);	
				calcResult.put(loopDate, totalValue);
				
				c.setTime(loopDate);
				c.add(Calendar.DATE, 1);
				loopDate = c.getTime();
				
				
			}
			
			
		


		}



		return calcResult;

	}





}
