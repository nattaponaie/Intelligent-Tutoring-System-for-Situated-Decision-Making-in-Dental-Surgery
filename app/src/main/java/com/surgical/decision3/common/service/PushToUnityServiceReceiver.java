package com.surgical.decision3.common.service;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

import com.surgical.decision3.common.controller.LogUtils;

public class PushToUnityServiceReceiver extends BroadcastReceiver
{
	private static PushToUnityServiceReceiver instance;

	// text that will be read by Unity
	public static String text = "";

	// Triggered when an Intent is catched
	@Override
	public void onReceive(Context context, Intent intent)
	{
		LogUtils.addLog( "--> PushToUnityServiceReceiver.onReceive() ");
		
		// We get the data the Intent has
		String sentIntent = intent.getStringExtra(Intent.EXTRA_TEXT);
		if (sentIntent != null)
		{
			// We assigned it to our static variable
			text = sentIntent; //intentText;
			//System.out.println( "--> PushToUnityServiceReceiver onReceive, text: " + text );
			//Logger.addRecordToLog( "--> PushToUnityServiceReceiver onReceive, text: " + text );
			
			LogUtils.addLog( "--> PushToUnityServiceReceiver.onReceive() , Text : " + text );
		}
	}

	// static method to create our receiver object, it'll be Unity that will
	// create ou receiver object (singleton)
	public static void createInstance()
	{
		if (instance == null)
		{
			instance = new PushToUnityServiceReceiver();
		}

	}
}
