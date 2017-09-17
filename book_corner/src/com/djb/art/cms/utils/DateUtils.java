package com.djb.art.cms.utils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class DateUtils {
	
	private static SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
	
	public static String serializeDate(Date date){
		return DateUtils.formatter.format(date);
	}
	
	public static Date deserializeDate(String date) throws ParseException{
		return DateUtils.formatter.parse(date);
	}
	
}
