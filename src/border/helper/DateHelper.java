package border.helper;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class DateHelper {
	// Suppress default constructor for noninstantiability
	private DateHelper() {
		throw new AssertionError();
	}

	public static Date getFutureDate() {
		Date res = new Date();

		DateFormat formatter;
		formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		try {
			res = (Date) formatter.parse("9999-12-31 23:59:59");
		} catch (ParseException e) {
			e.printStackTrace();
		}

		return res;
	}

	public static Date getNow() {
		Date res = new Date();
		return res;
	}
	
	
	public static Date getParsedDate(String dateStr) {
		Date res = new Date();

		DateFormat formatter;
		formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		try {
			res = (Date) formatter.parse(dateStr);
		} catch (ParseException e) {
			e.printStackTrace();
		}

		return res;
	}
	
}
