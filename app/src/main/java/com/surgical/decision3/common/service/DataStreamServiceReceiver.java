package com.surgical.decision3.common.service;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

public class DataStreamServiceReceiver extends BroadcastReceiver
{
	private static DataStreamServiceReceiver instance;

	// text that will be read by Unity
	public static String text = "";
	
	@Override
	public void onReceive(Context context, Intent intent)
	{
//		super();
	}
	
	public static void createInstance()
	{
		if (instance == null)
		{
			instance = new DataStreamServiceReceiver();
		}

	}
	
	public static void setDataStream( String textIn)
	{
		text = textIn;
	}
}
