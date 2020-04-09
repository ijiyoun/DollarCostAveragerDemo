package application;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
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

		int i = 0;


		if (date.compareTo(data.get(i).getDate()) == 0) { //if statement for the data point
			dataInstance = data.get(i);
		} else {

			while ((date.compareTo(data.get(i).getDate()) > 0) && (i < data.size())) {

				if (date.compareTo(data.get(i + 1).getDate()) == 0) { //assign the value if the date matches exactly
					dataInstance = data.get(i + 1);
				} else if (date.compareTo(data.get(i + 1).getDate()) < 0) { //assign the closest preview value if the date doesn't match exactly
					dataInstance = data.get(i);
					//System.out.println(i + "," + dataInstance);
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



}
