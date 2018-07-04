package com.surgical.decision3.common.controller;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.HashMap;

/**
 * Created by Dell on 7/7/2017.
 */

public class InterventionController2
{
    /**
     * Function to extract the key value from text file into map
     * */
    public static void initializeInterventionMap(InputStream file, HashMap<String, String> map)
    {
        BufferedReader bfr;
        try {

            bfr = new BufferedReader(new InputStreamReader(file, "UTF-8"));
            String line;

            while ((line = bfr.readLine()) != null)
            {
                //skip blank line
                if(line.trim().length() == 0)
                {
                    continue;
                }

                //skip comments
                if (line.substring(0, 2).equals("//"))//keep checking if that line not contain predicate then break;
                {
                    continue;
                }

                String[] keys = line.split("=");
                map.put(keys[0], keys[1]);
            }

            bfr.close();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}

