/**
 * Reads historical data to HistoricalData from API
 * @author yayan
 *
 */
public class ReaderAPI {

	/**
	 * Read price data to HistoricalData from API
	 * e.g. https://financialmodelingprep.com/api/v3/historical-price-full/AAPL?serietype=line
	 * @return
	 */
	static public HistoricalData readPriceFromAPI () {


	//WIP - need to be updated


		//https://devqa.io/java/how-to-parse-json-in-java




		/*
		URL url = new URL("https://financialmodelingprep.com/api/v3/historical-price-full/LK?serietype=line");

		try (BufferedReader reader = new BufferedReader(new InputStreamReader(url.openStream(), "UTF-8"))) {
		    for (String line; (line = reader.readLine()) != null;) {
		    System.out.println(line);
		  }
		}

		 */	


		//public static void main(String[] args) throws UnsupportedEncodingException, IOException {

		
		return null;
	}
	
	/**
	 * Read dividend data to HistoricalData from API
	 * e.g. https://financialmodelingprep.com/api/v3/historical-price-full/stock_dividend/AAPL
	 * @return
	 */
	static public HistoricalData readDividendFromAPI () {


			
			return null;
		}
}
