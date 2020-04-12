package application;
import java.text.ParseException;
import java.text.SimpleDateFormat;
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



	}
	/**
	 * Pulls HistoricalDataInstance with target date if it exists, 
	 * or if it does not - the closest one BEFORE the date
	 * @param date
	 * @return
	 */

	public HistoricalDataInstance pullClosestDataInstance (Date date) {



		HistoricalDataInstance dataInstance = new HistoricalDataInstance(null, null, null);

		
		Collections.sort(data);
		
		
		
		int i = 0;


		if (date.before(data.get(0).getDate())) { 
			dataInstance = data.get(0); 
		} else {

			while (date.after(data.get(i).getDate()) && (i < data.size() - 1)) {
				Date dataDate = data.get(i).getDate();
				if (date.before(dataDate)) dataInstance = data.get(i);
				if (date.equals(dataDate)) dataInstance = data.get(i);
				if (i == data.size() - 1) {
					dataInstance = data.get(i);
				} else {
					if (date.after(dataDate)) dataInstance = data.get(i + 1);
				}
				
				i++; 
			}


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
