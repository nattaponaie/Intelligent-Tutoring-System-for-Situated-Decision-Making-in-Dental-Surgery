package com.surgical.decision3.common.service;

import android.app.Service;
import android.content.Intent;
import android.os.Handler;
import android.os.IBinder;

import com.surgical.decision3.common.controller.LogUtils;
import com.surgical.decision3.logger.Logger;

//Class to send message to Unity. 
//Push message to Unity
public class PushToUnityService extends Service
{
	   // Handler to send data each second
		private final Handler handler = new Handler();

		//private int numIntent;
		//private static int serviceStartId = 0;
		
		private String extraIntentText;		//for catching data from intent at start. Note that at start, there

		// It's the code we want our Handler to execute to send data
		private Runnable sendData = new Runnable()
		{
			// the specific method which will be executed by the handler
			public void run()
			{
				LogUtils.addLog( "--> PUSH TO UNITY SERVICE.RUN()"  );
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
				sendIntent.setAction("com.surgical.decision.common.service.PushToUnityService");
				
				//NOTE: the value of setAction must be inside the Unity AndroidManifest.xml file
				//sendIntent.setAction("com.surgical.decision.common.service.PushToUnityService");

				// Here we fill the Intent with our data, here just a string with an
				// incremented number in it.
				if(!extraIntentText.equals( null ))
				{
					sendIntent.putExtra(Intent.EXTRA_TEXT, extraIntentText );
				}
				
				// And here it goes ! our message is send to any other app that want
				// to listen to it.
				sendBroadcast(sendIntent);

				handler.removeCallbacks(this);		//remove runable from the queue
				//handler.postDelayed(this, 1000);	//Cause runnable to be in the queue and run at the time elapse (infinite run)
				
				//stopSelf( serviceStartId );		//Call to stop service in case of too many services starts.
				stopSelf();  //ONCE the broadcast was sent, stop the service. 
				//System.out.println( "--> PushToUnityService stopSelf(), startid: " + serviceStartId );
			}
		};

		// When service is started
		@Override
		public void onStart(Intent intent, int startid)
		{
			//serviceStartId = startid;
			//Toast.makeText(this, "PushToUnityService onStart", Toast.LENGTH_LONG).show();
			
			//numIntent = 0;
			extraIntentText = intent.getStringExtra(Intent.EXTRA_TEXT);
			//System.out.println( "--> PushToUnityService onStart, startid: " + serviceStartId );
			Logger.addRecordToLog( "--> PushToUnityService onStart, text: " + extraIntentText );
			
			// We first start the Handler
			handler.removeCallbacks(sendData);
			//handler.postDelayed(sendData, 1000);
			handler.post(sendData);
		}

		@Override
		public IBinder onBind(Intent intent)
		{
			// TODO Auto-generated method stub
			return null;
		}
}
