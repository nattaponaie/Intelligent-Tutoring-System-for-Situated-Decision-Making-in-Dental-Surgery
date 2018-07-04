package com.surgical.decision3.common.constant;


public class MainConstants
{
	//TimeZone
	public static String TIME_ZONE_BANGKOK = "Asia/Bangkok";
	
	//Development Mode
	public static boolean TEST_ON_ANDROID_MODE = false;
	public static boolean TEST_ON_ANDROID_MODE_YES = true ;
	public static boolean TEST_ON_ANDROID_MODE_NO= false ;
	
	public static void setTestMode( boolean isTestOnAndroid )
	{
		TEST_ON_ANDROID_MODE = isTestOnAndroid;
	}
	
	
	//Run Mode
	public static String EM_MODE = "EMULATOR";
	public static String SAMSUNG_GRAND2 = "SAMSUNG_G2_5_1";
	
	//INSTALLATION PATH
//	public static String EMULATOR_5_1_PATH = "/data/SDM/engine";
//	public static String SUMSUNG_GRAND2_PATH = "/storage/extSdCard/SDM/engine";
	public static String APPLICATION_ROOT_PATH = "desitra2.0";
	//public static String APPLICATION_ROOT_PATH = 
	
	//IMAGE Path
	public static String getApplicationRootPath( String mode )
	{
//		if
//		APPLICATION_ROOT_PATH
		return null;
	}
	
//	private static MainConstants instance = null;
//	
//	MainConstants()
//	{
//		
//	}
//	
//	public static MainConstants getInstance()
//	{
//		if( instance == null )
//		{
//			instance = new MainConstants();
//		}
//		return instance;
//	}
}

