package application;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

/**
 * Utility class 
 * @author yayan
 *
 */
public class Util {

	/**
	 * Quick method to parse "yyy-MM-dd" String to date
	 * @param date
	 * @return
	 */
	public static Date parseDate(String date) {
		try {
			return new SimpleDateFormat("yyy-MM-dd").parse(date);
		} catch (ParseException e) {
			return null;
		}
	}
	
	/**
	 * adds n days to input date
	 * @param date
	 * @return
	 */
	public static Date calcDate(Date date, int n) {
		Date newDate;
		
		Calendar c = Calendar.getInstance();
		c.setTime(date);
		c.add(Calendar.DATE, n);
		newDate = c.getTime();
		
		return newDate;
	}
	
}
