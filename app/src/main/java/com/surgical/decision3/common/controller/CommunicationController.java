package com.surgical.decision3.common.controller;

import android.content.Context;
import android.content.Intent;

import com.surgical.decision3.common.bean.datastream.DataStream;
import com.surgical.decision3.common.bean.intervention.Intervention;
import com.surgical.decision3.common.bean.response.Response;
import com.surgical.decision3.common.service.PushToUnityService;
import com.surgical.decision3.logger.LogUtil;

import org.simpleframework.xml.Serializer;
import org.simpleframework.xml.convert.AnnotationStrategy;
import org.simpleframework.xml.core.Persister;

import java.io.StringWriter;

public class CommunicationController
{
	private static CommunicationController instance;
	
	public static void createInstance()
	{
		if (instance == null)
		{
			instance = new CommunicationController();
		}
	}
	
	public static CommunicationController getInstance()
	{
		return instance;
	}

	public static void sendDataToUnity(Context context, String dataString )
	{
		LogUtils.addLog( "--> COMMUNICATION_CONTROLLER.sendDataToUnity(), Context: " + context+ ",  String: " + dataString + ".");
		
		//LogUtils.logInterventionXML( dataString );	//COMMENT FOR TEST ONLY. TODO: REMOVE COMMENT after TEST.
		
		// CALL SERVICE TO SEND DATA TO UNITY when user click Send button
		// SEND DATA FROM ANDROID TO UNITY
		Intent intent = new Intent(context, PushToUnityService.class);
		intent.putExtra(Intent.EXTRA_TEXT, dataString );
//		startService(intent);
		context.startService( intent );

		
	}

	/*
	Response response = new Response();
		response.setIntervention( i );

		return response;
	* */

	public static String transformResponseIntoXML( Intervention intervention )
	{

		Response response = new Response();
		response.setIntervention( intervention );

		String xmlString = null;
		Serializer serializer = new Persister( new AnnotationStrategy() );
		try
		{
			StringWriter stringWriter = new StringWriter();
			serializer.write( response, stringWriter );

			xmlString = stringWriter.toString();

			System.out.println( "============ RESPONSE XML (START) ============" );
			System.out.println( stringWriter.toString() );
			System.out.println( "============ RESPONSE XML (END) ============" );
		}
		catch ( Exception e )
		{
			e.printStackTrace();
		}

		return xmlString;
	}


	public static DataStream transformDataStreamXMLToJavaObject(String dataStreamXML )
	{
		System.out.println( "============ DATASTREAM XML (START) ============" );
		System.out.println( dataStreamXML );
		System.out.println( "============ DATASTREAM XML (END) ============" );

		LogUtils.addLog( "--> DATASTREAM_CONTROLLER.transformDataStreamXMLToJavaObject(),  ");

		//Predefine the xml

		dataStreamXML = dataStreamXML.replace( "xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\"", "" );
		dataStreamXML = dataStreamXML.replace( "xmlns:xsd=\"http://www.w3.org/2001/XMLSchema\"" , "");
		dataStreamXML = dataStreamXML.replace( "\"", "'" );
		dataStreamXML = dataStreamXML.replace( "<?xml version='1.0' encoding='UTF-8'?>", "" );
		dataStreamXML = dataStreamXML.replace( "<?xml version='1.0' encoding='utf-8'?>", "" );


		Serializer serializer = new Persister( new AnnotationStrategy() );
		DataStream dt = null;
		try
		{
			dt = serializer.read( DataStream.class, dataStreamXML );
			System.out.println( dt.toString() );

			String logText = "[ DataStreamController.setDataStreamXML()]" + "[DATE/TIME:]" + LogUtil.getCurrentDateTime() + "\n"
					+ "--> DataStreamController, setDataStreamXML()\n"
					+ "    >> dataStreamXML (param): \n"
					+ dataStreamXML + "\n";
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

}
