package com.surgical.decision3.logger;

import java.text.SimpleDateFormat;
import java.util.Date;

public class LogUtil
{
	public static LogUtil instance;

	public static void createInstance()
	{
		if (instance == null)
		{
			instance = new LogUtil();
			Logger.addRecordToLog( "--> LogUtil, createInstance()" );
		}
	}

	public static LogUtil getInstance()
	{
		if (instance == null)
		{
			createInstance();
		}

		return instance;
	}

//	public static void setInstance(LogUtil instance)
//	{
//		LogUtil.instance = instance;
//	}

	public static SimpleDateFormat dateFormat = new SimpleDateFormat(
			"yyyy/MM/dd HH:mm:ss" );

	public static String getCurrentDateTime()
	{
		return dateFormat.format( new Date() );
	}

}
