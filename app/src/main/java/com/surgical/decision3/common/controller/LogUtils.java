package com.surgical.decision3.common.controller;

import com.surgical.decision3.logger.Logger;

import java.text.SimpleDateFormat;
import java.util.Date;

public class LogUtils
{
	public static void addLog(String logString )
	{
//		SimpleDateFormat dateFormat = new SimpleDateFormat(
//				"yyyy/MM/dd HH:mm:ss" );
//		String currentDateandTime = dateFormat.format( new Date() );
//
//		String logText = "[ UNITY > ANDROID ]" + "[DATE/TIME:]"
//				+ currentDateandTime + "\n"
//				+ "--> LogUtils.addLog()\n"
//				+ "    >> logString : \n" + logString + "\n";
		Logger.addRecordToLog( logString );
	}
	
	public static void logIncomingXML(String xmlString)
	{
		SimpleDateFormat dateFormat = new SimpleDateFormat(
				"yyyy/MM/dd HH:mm:ss" );
		String currentDateandTime = dateFormat.format( new Date() );

		String logText = "[ UNITY > ANDROID ]" + "[DATE/TIME:]"
				+ currentDateandTime + "\n"
				+ "--> LogUtils.logIncomingXML()\n"
				+ "    >> xmlString (param): \n" + xmlString + "\n";
		Logger.addRecordToLog( logText );
	}
	
	
	public static void logInterventionXML(String xmlString)
	{
		SimpleDateFormat dateFormat = new SimpleDateFormat(
				"yyyy/MM/dd HH:mm:ss" );
		String currentDateandTime = dateFormat.format( new Date() );

		String logText = "[ ANDROID > ANDROID ]" + "[DATE/TIME:]"
				+ currentDateandTime + "\n"
				+ "--> LogUtils.logInterventionXML()\n"
				+ "    >> xmlString (param): \n" + xmlString + "\n";
		Logger.addRecordToLog( logText );
	}
	
}
