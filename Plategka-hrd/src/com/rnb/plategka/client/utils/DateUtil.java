/**
 * 
 */
package com.rnb.plategka.client.utils;

import java.text.SimpleDateFormat;

import com.google.gwt.i18n.client.DateTimeFormat;




/**
 * @author budukh-rn
 *
 */
public final class DateUtil {

	/**
	 * "dd.MM.yyyy HH:mm"
	 */
	public static final String DEFAULT_DATE_TIME_FORMAT = "dd.MM.yyyy HH:mm";
	
	/**
	 * "dd.MM.yyyy"
	 */
	public static final String DEFAULT_DATE_FORMAT = "dd.MM.yyyy";
	
	/**
	 * format "dd.MM.yyyy"
	 */
	public static final DateTimeFormat DATE_FORMAT = DateTimeFormat.getFormat(DEFAULT_DATE_FORMAT);

	public static final DateTimeFormat DATE_TIME_FORMAT = DateTimeFormat.getFormat(DEFAULT_DATE_TIME_FORMAT);
	
	/**
	 * 
	 */
	public DateUtil() {
	}

}
