package com.okonna.ios.util;

import java.util.Date;

public class SQLLiteData
{
	//Prevent instantiations
	private SQLLiteData()
	{
	}
	
	public static Date convertDate(String strToConvert)
	{
		return new Date((Long.parseLong(strToConvert) * 1000));
	}

	public static String convertDate(Date dteToConvert)
	{
		return Long.toString((dteToConvert.getTime() / 1000));
	}
}
