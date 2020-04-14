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


		//https://devqa.io/java/how-to-parse-json-in-java

		String urlString = "https://financialmodelingprep.com/api/v3/historical-price-full/"+ticker+"?serietype=line";

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


		System.out.println(json);

		System.out.println(JsonParser.parseString(json).getAsJsonObject().get("historical"));
		//System.out.println(JsonParser.parseString(json).getAsJsonArray());
		
		JsonObject jsonObject = JsonParser.parseString(json).getAsJsonObject();
		System.out.println(jsonObject.get("historical"));

		System.out.println(jsonObject.get("historical").getAsJsonArray().get(1));

	
		
		System.out.println(jsonObject.get("symbol").getAsString());

		JsonArray arr = jsonObject.getAsJsonArray("historical");
		for (int i = 0; i < arr.size(); i++) {
			String post_id = arr.get(i).getAsJsonObject().get("date").getAsString();
			String price = arr.get(i).getAsJsonObject().get("close").getAsString();
			System.out.println(post_id + price);
		}

			/*
		URL url = new URL("https://financialmodelingprep.com/api/v3/historical-price-full/LK?serietype=line");

		try (BufferedReader reader = new BufferedReader(new InputStreamReader(url.openStream(), "UTF-8"))) {
		    for (String line; (line = reader.readLine()) != null;) {
		    System.out.println(line);
		  }
		}

			 */	




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
