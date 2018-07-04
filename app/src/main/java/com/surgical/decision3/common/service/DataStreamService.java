package com.surgical.decision3.common.service;

import android.app.Service;
import android.content.Intent;
import android.os.Handler;
import android.os.IBinder;

public class DataStreamService extends Service
{
	public static Boolean isServiceStart = false;
	public static DataStreamService instance;
	
	
	// Handler to send data each second
	private final Handler handler = new Handler();
	private String extraIntentText;		//for catching data from intent at start. Note that at start, there 

	// It's the code we want our Handler to execute to send data
	private Runnable sendData = new Runnable()
	{
		// the specific method which will be executed by the handler
		public void run()
		{
			// sendIntent is the object that will be broadcast outside our app
			Intent sendIntent = new Intent();

			// We add flags for example to work from background
			sendIntent.addFlags( Intent.FLAG_ACTIVITY_NO_ANIMATION
							   | Intent.FLAG_FROM_BACKGROUND
							   | Intent.FLAG_INCLUDE_STOPPED_PACKAGES);

			// SetAction uses a string which is an important name as it
			// identifies the sender of the intent and that we will give to the
			// receiver to know what to listen.
			// By convention, it's suggested to use the current package name
			//sendIntent.setAction("com.surgical.decision.common.service");
			
			//NOTE: the value of setAction must be inside the Unity AndroidManifest.xml file
			sendIntent.setAction("com.surgical.decision.common.service.MyService");

			// Here we fill the Intent with our data, here just a string with an
			// incremented number in it.
			//sendIntent.putExtra(Intent.EXTRA_TEXT, "XXX: Intent " + numIntent);
			if(!extraIntentText.equals( null ))
			{
				sendIntent.putExtra(Intent.EXTRA_TEXT, extraIntentText );
			}

			// And here it goes ! our message is send to any other app that want
			// to listen to it.
			sendBroadcast(sendIntent);

			handler.removeCallbacks(this);		//remove runable from the queue
		}
	};

	// When service is started
	@Override
	public void onStart(Intent intent, int startid)
	{
		if( !isServiceStart )
		{
		extraIntentText = intent.getStringExtra( Intent.EXTRA_TEXT );
		// We first start the Handler
		handler.removeCallbacks(sendData);
		handler.post( sendData );
		
		isServiceStart = true;
		}
		
	}
	
	// static method to create our service object, it'll be Unity that will
	// create ou receiver object (singleton)
	public static void createInstance()
	{
		if (instance == null)
		{
			instance = new DataStreamService();
		}
	}
	
	@Override
	public IBinder onBind(Intent intent)
	{
		return null;
	}

}
