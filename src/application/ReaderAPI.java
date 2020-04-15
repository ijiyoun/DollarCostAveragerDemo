package application;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.MalformedURLException;
import java.net.URL;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
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
	static public HistoricalData readPriceFromAPI (String ticker) {

		HistoricalData output = new HistoricalData();

		//pulling data from the API
		//https://financialmodelingprep.com/api/v3/historical-price-full/LK?serietype=line
		String urlString = "https://financialmodelingprep.com/api/v3/historical-price-full/"+ticker+"?serietype=line"; 
		JsonObject jsonObject = pullFromAPI(urlString);

		JsonArray arr = jsonObject.getAsJsonArray("historical");
		for (int i = 0; i < arr.size(); i++) {
			String date = arr.get(i).getAsJsonObject().get("date").getAsString();
			Double value = arr.get(i).getAsJsonObject().get("close").getAsDouble();
			HistoricalDataInstance newInstance = new HistoricalDataInstance(ticker, Util.parseDate(date), value);
			output.addNewEntry(newInstance);

		}

		return output;
	}

	/**
	 * Read dividend data to HistoricalData from API
	 * e.g. https://financialmodelingprep.com/api/v3/historical-price-full/stock_dividend/AAPL
	 * @return
	 */
	static public HistoricalData readDividendFromAPI (String ticker) {

		HistoricalData output = new HistoricalData();

		//pulling data from the API
		//https://financialmodelingprep.com/api/v3/historical-price-full/stock_dividend/AAPL
		String urlString = "https://financialmodelingprep.com/api/v3/historical-price-full/stock_dividend/" + ticker; 
		JsonObject jsonObject = pullFromAPI(urlString);

		JsonArray arr = jsonObject.getAsJsonArray("historical");
		for (int i = 0; i < arr.size(); i++) {
			String date = arr.get(i).getAsJsonObject().get("date").getAsString();
			Double value = arr.get(i).getAsJsonObject().get("adjDividend").getAsDouble();
			HistoricalDataInstance newInstance = new HistoricalDataInstance(ticker, Util.parseDate(date), value);
			output.addNewEntry(newInstance);

		}

		return output;

	}

	static public JsonObject pullFromAPI (String urlString) {

		URL url = null;
		try {
			url = new URL(urlString);
		} catch (MalformedURLException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}

		String json= "";

		try (BufferedReader reader = new BufferedReader(new InputStreamReader(url.openStream(), "UTF-8"))) {
			for (String line; (line = reader.readLine()) != null;) {
				// System.out.println(line);
				json += line;
			}
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		//converting JSON
		JsonObject jsonObject = JsonParser.parseString(json).getAsJsonObject();

		return jsonObject;
	}
}
