import java.text.ParseException;
import java.text.SimpleDateFormat;
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
	
}
