package utils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class DateUtils {

	public static String format(Date date,String pattern) {
		SimpleDateFormat df = new SimpleDateFormat(pattern);
		return df.format(date);
	}
	
	public static Date format(String date,String pattern) {
		SimpleDateFormat df = new SimpleDateFormat(pattern);
		try {
			return df.parse(date);
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return new Date();
	}
	
	public static Date now(String pattern){
		Date date = new Date();
		SimpleDateFormat df = new SimpleDateFormat(pattern);
		try {
			return df.parse(format(date,pattern));
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return new Date();
	}
}
