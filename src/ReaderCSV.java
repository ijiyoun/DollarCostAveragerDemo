import java.io.File;
import java.io.FileNotFoundException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Scanner;

/**
 * Reads historical data to HistoricalData from CSV
 * @author yayan
 *
 */
public class ReaderCSV {

	/**
	 * Static function to read from CSV
	 * @param filename
	 * @return HistoricalData
	 */
	static public HistoricalData readFromCSV (String filename)  {

		HistoricalData data = new HistoricalData(); 
		File file = new File (filename);

		try {
			Scanner s = new Scanner (file);
			s.nextLine();
			while (s.hasNextLine()) {
				String line = s.nextLine();
				//	System.out.println(line);
				String[] string = line.split(",");

				//get ticker
				String ticker = string[0];

				//get date	
				Date date = Util.parseDate(string[1]);

				//get value (either dividend or price)	
				Double value = Double.valueOf(string[2]);	


				//initialize a new instance
				HistoricalDataInstance newEntry = new HistoricalDataInstance(ticker, date, value);



				//add to the historical data
				data.addNewEntry(newEntry);


			}

			s.close();
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}





		return data;

	}

}
