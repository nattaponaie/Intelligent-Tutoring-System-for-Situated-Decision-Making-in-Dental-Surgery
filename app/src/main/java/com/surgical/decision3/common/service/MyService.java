package com.surgical.decision3.common.service;

import android.app.Service;
import android.content.Intent;
import android.os.Handler;
import android.os.IBinder;

public class MyService extends Service
{
	// Handler to send data each second
	private final Handler handler = new Handler();

	//private int numIntent;
	
	private String extraIntentText;		//for catching data from intent at start. Note that at start, there 

	// It's the code we want our Handler to execute to send data
	private Runnable sendData = new Runnable()
	{
		// the specific method which will be executed by the handler
		public void run()
		{
			//numIntent++;

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

			//NUI: TODO: MAKE SURE THAT IT SEND TO UNITY ONLY ONCE.
			// In our case we run this method each second with postDelayed
			handler.removeCallbacks(this);		//remove runable from the queue
			//handler.postDelayed(this, 1000);   //Cause runnable to be in the queue and run at the time elapse (infinite run)
		}
	};

	// When service is started
	@Override
	public void onStart(Intent intent, int startid)
	{
		//numIntent = 0;
		extraIntentText = intent.getStringExtra(Intent.EXTRA_TEXT);
		// We first start the Handler
		handler.removeCallbacks(sendData);
		handler.postDelayed(sendData, 1000);
	}
	
	@Override
	public IBinder onBind(Intent intent)
	{
		return null;
	}

}
