package com.surgical.decision3.logger;


import android.os.Environment;
import android.util.Log;

import com.surgical.decision3.common.constant.MainConstants;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.logging.FileHandler;


/**
 * NOTE: Logger does not work. File creation is in error. 
 * Test only emulator only. the 
 * */

/**
 * @author Rakesh.Jha
 * Date - 07/10/2013
 * Definition - Logger file use to keep Log info to external SD with the simple method
 * Ref url: http://stackoverflow.com/questions/12623415/how-to-store-logs-in-a-txt-file-using-the-android-util-log
*/

public class Logger {

    public static  FileHandler logger = null;
   // public static String filepath = LoggerConstants.SUMSUNG_GRAND2_FILE_PATH; //EMULATOR_5_1_FILE_PATH; 
    
    //private static String filename = "ProjectName_Log";

    static boolean isExternalStorageAvailable = false;
    static boolean isExternalStorageWriteable = false; 
    static String state = Environment.getExternalStorageState();

    public static void addRecordToLog(String message) {

        System.out.println("toey "+Environment.getExternalStorageDirectory()
                + File.separator 							//  /
                + MainConstants.APPLICATION_ROOT_PATH 		//	SDM/engine
                + File.separator							//	/
                + LoggerConstants.FILE_NAME);
        System.out.println("toey "+Environment.getExternalStorageDirectory()
                + File.separator
                + MainConstants.APPLICATION_ROOT_PATH);

        if (Environment.MEDIA_MOUNTED.equals(state)) { 
            // We can read and write the media 
            isExternalStorageAvailable = isExternalStorageWriteable = true; 
        } else if (Environment.MEDIA_MOUNTED_READ_ONLY.equals(state)) { 
            // We can only read the media 
            isExternalStorageAvailable = true; 
            isExternalStorageWriteable = false; 
        } else { 
            // Something else is wrong. It may be one of many other states, but all we need 
            //  to know is we can neither read nor write 
            isExternalStorageAvailable = isExternalStorageWriteable = false; 
        }

        //File dir = new File("/sdcard/Files/Project_Name");
        //File dir = new File( filepath );
        
        File dir = new File( Environment.getExternalStorageDirectory()
        					+ File.separator 
        					+ MainConstants.APPLICATION_ROOT_PATH );

        if (Environment.MEDIA_MOUNTED.equals(state)) {  
            if(!dir.exists()) {
                Log.d("Dir created ", "Dir created ");
                dir.mkdirs();
            }

            //File logFile = new File( filepath + LoggerConstants.FILE_NAME + ".txt" );
            File logFile = new File( Environment.getExternalStorageDirectory() 
            						+ File.separator 							//  /
            						+ MainConstants.APPLICATION_ROOT_PATH 		//	SDM/engine
            						+ File.separator							//	/
            						+ LoggerConstants.FILE_NAME );				//	ddd.txt
            
            if (!logFile.exists())  {
                try  {
                    Log.d("File created ", "File created ");
                    logFile.createNewFile();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            try { 
                //BufferedWriter for performance, true to set append to file flag
                BufferedWriter buf = new BufferedWriter(new FileWriter(logFile, true)); 

                buf.write(message + "\r\n");
                //buf.append(message);
                buf.newLine();
                buf.flush();
                buf.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}