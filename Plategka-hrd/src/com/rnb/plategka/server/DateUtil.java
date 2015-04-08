package com.rnb.plategka.server;

import java.util.Calendar;
import java.util.Date;
import java.util.TimeZone;

/**
 * 
 * @author budukh-rn
 * 
 *
 */
public final class DateUtil {
	private static Calendar calendar = Calendar.getInstance(TimeZone.getTimeZone("EET"));

	synchronized public static Date getDate(int year, int month, int dayOfMonth, int hourOfDay, int minute, int second){
		calendar.set(Calendar.YEAR, year);
		calendar.set(Calendar.MONTH, month - 1);
		calendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);
		calendar.set(Calendar.HOUR_OF_DAY, hourOfDay);
		calendar.set(Calendar.MINUTE, minute);
		calendar.set(Calendar.SECOND, second);
		calendar.set(Calendar.MILLISECOND, 0);
		return calendar.getTime();
	}
	
	synchronized public static int getMonth(Date date){
		calendar.setTime(date == null ? new Date() : date);
		return calendar.get(Calendar.MONTH);
	}

	synchronized public static int getYear(Date date){
		calendar.setTime(date == null ? new Date() : date);
		return calendar.get(Calendar.YEAR);
	}

}
