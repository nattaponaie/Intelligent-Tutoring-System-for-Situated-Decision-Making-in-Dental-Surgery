package com.surgical.decision3.common.controller;

import com.surgical.decision3.common.bean.datastream.DataStream;
import com.surgical.decision3.logger.Logger;

import org.simpleframework.xml.Serializer;
import org.simpleframework.xml.convert.AnnotationStrategy;
import org.simpleframework.xml.core.Persister;

public class DataStreamController {
	private static DataStreamController instance;
	
	public static void createInstance()
	{
		if (instance == null)
		{
			instance = new DataStreamController();
			Logger.addRecordToLog( "--> DataStreamController, createInstance()");
		}
	}
	
	public static DataStream transformDataStreamXMLToJavaObject(String dataStreamXML )
	{
		LogUtils.addLog( "--> DATASTREAM_CONTROLLER.transformDataStreamXMLToJavaObject(),  ");
		
		//Predefine the xml
		dataStreamXML = dataStreamXML.replace( "xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\"", "" );
		dataStreamXML = dataStreamXML.replace( "xmlns:xsd=\"http://www.w3.org/2001/XMLSchema\"" , "");
		dataStreamXML = dataStreamXML.replace( "\"", "'" );
		dataStreamXML = dataStreamXML.replace( "<?xml version='1.0' encoding='UTF-8'?>", "" );	//NOTE input xml should be UTF-8 only, UTF-16 is not work with the text. 
		dataStreamXML = dataStreamXML.replace( "<?xml version='1.0' encoding='utf-8'?>", "" );
		
		System.out.println( "---------------------- XML after removing header ----------------------" );
		System.out.println(dataStreamXML);
		
		Serializer serializer = new Persister( new AnnotationStrategy() );
		DataStream dt = null;
		try
		{
			dt = serializer.read( DataStream.class, dataStreamXML );
			
			Logger.addRecordToLog( "" + dt.isRequireImmediateResponse() );
			
			System.out.println( dt.toString() );
			
//			String logText = "[ DataStreamController.setDataStreamXML()]" + "[DATE/TIME:]" + LogUtil.getCurrentDateTime() + "\n" 
//					+ "--> DataStreamController, setDataStreamXML()\n"
//					+ "    >> dataStreamXML (param): \n"
//					+ dataStreamXML + "\n";
			//Logger.addRecordToLog( logText );
		}
		catch ( Exception e )
		{
			LogUtils.addLog( "--> DATASTREAM_CONTROLLER.transformDataStreamXMLToJavaObject(),  EXCEPTION:  ");
			LogUtils.addLog( e.getMessage() );
			
			e.printStackTrace();
		}
		
		return dt;
	}
	
	
//	
//	public static void setDataStreamXML( String dataStreamXML )
//	{
//		Serializer serializer = new Persister( new AnnotationStrategy() );
//		try
//		{
//			DataStream dt = serializer.read( DataStream.class, dataStreamXML);
//			
//			String logText = "[ DataStreamController.setDataStreamXML()]" + "[DATE/TIME:]" + LogUtil.getCurrentDateTime() + "\n" 
//					+ "--> DataStreamController, setDataStreamXML()\n"
//					+ "    >> dataStreamXML (param): \n"
//					+ dataStreamXML + "\n";
//			Logger.addRecordToLog( logText );
//		}
//		catch ( Exception e )
//		{
//			e.printStackTrace();
//			Logger.addRecordToLog( e.getMessage() );
//		}
//	}
}
