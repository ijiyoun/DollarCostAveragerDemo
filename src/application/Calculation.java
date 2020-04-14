package application;
import java.util.*;

/**
 * Class to perform DollarCostAveraging calculations 
 * @author yayan
 * 
 * HistoricalData priceData; 
 * HistoricalData dividendData; 
 * HistoricalData foreignExchangeData;
 *
 */
public class Calculation {

	Portfolio myPortfolio;
	HistoricalData priceData; 
	HistoricalData dividendData; 
	HistoricalData foreignExchangeData;
	Date minimum;
	Date maximum;
	String dataLimitedBy;

	public Calculation (Portfolio myPortfolio, HistoricalData priceData, HistoricalData dividendData, HistoricalData foreignExchangeData) {
		this.priceData = priceData;
		this.dividendData = dividendData;
		this.foreignExchangeData = foreignExchangeData;
		this.myPortfolio = myPortfolio;
		
		//calculate minimum/maximum date range
		minimum = Collections.min(priceData.getData()).getDate();
		maximum = Collections.max(priceData.getData()).getDate();
		dataLimitedBy = "price data";
		
	}

	/**
	 * Main method that returns Date/Double HashMap based on DollarCostAveraging calculations
	 * @param startDateString
	 * @param endDateString
	 * @param additionalInvestment
	 * @param currency
	 * @param numDaysBetweenInvestment
	 * @param reinvest
	 * @param commission
	 * @param dividendTax
	 * @return
	 */
	public HashMap<Date, Double> calculateDollarCostAveraging (String ticker, String startDateString, String endDateString, Double additionalInvestment, String currency, int numDaysBetweenInvestment, Boolean reinvest, Double commission, Double dividendTax) {


		HashMap<Date, Double> calcResult = new HashMap<Date, Double>();



		//set start/end date for Dollar Cost Averaging
		Date startDate = Util.parseDate(startDateString);
		Date endDate = Util.parseDate(endDateString);
		Date loopDate = startDate;
		Date nextDateToAddCash = startDate;
		
		//filter historical data for the current data
		HistoricalData priceDataFiltered = priceData.filterByTicker(ticker);
		HistoricalData dividendDataFiltered = dividendData.filterByTicker(ticker);


		Double totalInvested = 0.0; //hold aggregated investment amount to pass to Portfolio cashFlow

		//loop over investment period from start to end date day by day
		while (loopDate.compareTo(endDate) <= 0) {


			//add dividends if they are distributed on this day
		/**	if (dividendData.exist(loopDate) && reinvest) {
				Double totalQuantity = myPortfolio.getTotalQuantityByTicker("");
				Double dividends = totalQuantity * dividendData.pullClosestDataInstance(loopDate).getValue() * (1 - dividendTax); //after tax
				myPortfolio.addCashBalance(dividends);
				System.out.println("divid "+dividends + " div rate "+dividendData.pullClosestDataInstance(loopDate).getValue());

			}

**/			//cash all previously calculated dividends
			myPortfolio.cashDividendsPendingReceival(loopDate);
			//add dividends if they are distributed on this day			
			if (dividendDataFiltered.exist(loopDate)) {
				
				myPortfolio.addDividendsToPendingReceival(loopDate, dividendDataFiltered, dividendTax);
				

			}



			//add incremental cash if equals next investment date
			if (loopDate.equals(nextDateToAddCash)) {

				//add code to exchange incremental investment to USD if needed

				myPortfolio.addCashBalance(additionalInvestment);
				
				//store aggregated total investment amount
				totalInvested += additionalInvestment;
				myPortfolio.addCashFlow(loopDate, totalInvested);

				//calculate date for the next investment
				nextDateToAddCash = Util.calcDate(nextDateToAddCash, numDaysBetweenInvestment);
				
			}




			//purchase assets if have cash (should have more than commission to buy)

			Double cashToInvest = myPortfolio.getCashBalance();
			if (!reinvest) {
				cashToInvest = cashToInvest - myPortfolio.getAccumulatedDividends() + myPortfolio.getDividendPendingReceivalTotal()	;
			}
			
			
			if (cashToInvest > commission) {

					myPortfolio.buyAssetByAmount("", cashToInvest,commission, loopDate, priceDataFiltered.pullClosestDataInstance(loopDate).getValue());
				
			}



			//calculate total value and add to the output HashMpap
			Double totalValue = myPortfolio.updateAndReturnTotalValue(loopDate, priceDataFiltered);	
			calcResult.put(loopDate, totalValue);


		//	System.out.println(loopDate + " cashbalance " + myPortfolio.getCashBalance() + " currentvalue " + totalValue + " quantity total "+myPortfolio.getTotalQuantityByTicker("") + "dividends "+myPortfolio.getAccumulatedDividends());

			//+1 day to the loopDate counter 
			loopDate = Util.calcDate(loopDate, 1);

		



		}


		return calcResult;

	}

	public Portfolio getPortfolio () {
		return myPortfolio;
	}




}
