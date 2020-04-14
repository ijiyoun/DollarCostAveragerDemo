package application;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;

/**
 * Stores all historical data
 * @author yayan
 *
 */

public class HistoricalData {


	private ArrayList<HistoricalDataInstance> data;

	public HistoricalData () {

		ArrayList<HistoricalDataInstance> data = new ArrayList<HistoricalDataInstance> ();
		this.data = data;

	}

	/**
	 * adds each new HistoricalDataInstance to the ArrayList
	 * @param newEntry
	 */
	public void addNewEntry (HistoricalDataInstance newEntry) {

		data.add(newEntry);
		Collections.sort(data);


	}

	/**
	 * filter a historical data object and returns
	 * data relevant to one ticker only
	 * @param ticker
	 * @return HistoricalData
	 */
public HistoricalData filterByTicker (String ticker) {
	HistoricalData filtered = new HistoricalData();
	
	for (HistoricalDataInstance current: data) {
		if (current.getTicker().equals(ticker)) {
			filtered.addNewEntry(current);
		}
		
		
		
	}
		
	
	
	return filtered;
}
	
	
	
	/**
	 * Pulls HistoricalDataInstance with target date if it exists, 
	 * or if it does not - the closest one BEFORE the date
	 * @param date
	 * @return
	 */

	public HistoricalDataInstance pullClosestDataInstance (Date date) {



		HistoricalDataInstance dataInstance = new HistoricalDataInstance(null, null, null);


		



		int i = 0;
		//boolean found = false;



		while (dataInstance.getDate() == null) {


			if (date.before(data.get(0).getDate())) {
				dataInstance = data.get(0);
			} else if (date.equals(data.get(i).getDate())) {
				dataInstance = data.get(i);

			} else if (i + 1 == data.size()) {
				dataInstance = data.get(data.size() - 1);
			} else if ((date.after(data.get(i).getDate())) && (date.before(data.get(i + 1).getDate()))) {
				dataInstance = data.get(i);
			}

			i++; 
		}




		return dataInstance;
	}

	/**
	 * returns true if HistoricalDataInstance with certain date exists
	 * @param date
	 * @return
	 */

	public boolean exist (Date date) {

		boolean exist = false;

		for (HistoricalDataInstance currentInstance : data) {
			if (date.equals(currentInstance.getDate())) {
				exist = true;
			}

		}

		return exist;
	}


	/**
	 * returns main ArrayList 
	 * @return data
	 */
	public ArrayList<HistoricalDataInstance> getData() {
		return data;
	}


	/**
	 * returns HashMap of the HistoricalData
	 * @return
	 */
	public HashMap<Date, Double> returnHashMap () {

		HashMap<Date, Double> allData = new HashMap <Date, Double>();

		for (HistoricalDataInstance currentInstance : data) {
			allData.put(currentInstance.getDate(), currentInstance.getValue());

		}
		return allData;
	}


	/**
	 * Returns HashMap of the HistoricalData based on start and end dates
	 * TODO: Will add Ticker later on. 
	 */
	public HashMap<Date, Double> returnHashMap (String startDateString, String endDateString) {

		Date startDate = Util.parseDate(startDateString);
		Date endDate = Util.parseDate(endDateString);


		HashMap<Date, Double> allData = new HashMap <Date, Double> ();

		for (HistoricalDataInstance currentInstance : data) {
			if (startDate.before(currentInstance.getDate()) && endDate.after(currentInstance.getDate())) {
				allData.put(currentInstance.getDate(), currentInstance.getValue());
			}
		}

		return allData;	
	}







}
