package com.core.ems.util;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class DateUtil {
	
    public static String getFormattedDateString(Date date) {
    	SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
		return date != null ? dateFormat.format(date) : null;
    }

    public static Date getDate(String dateStr) {
    	Date date = null;
    	SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
    	try {
    		date = dateFormat.parse(dateStr);
    	} catch(ParseException e) {
    		e.printStackTrace();
    		date = null;
    	}
		return date;
    }

/*    public static void main(String[] args) {
		System.out.println(getFormattedDateString(new Date()));
		System.out.println(getDate("02/12/1983"));
	}
*/    
}
