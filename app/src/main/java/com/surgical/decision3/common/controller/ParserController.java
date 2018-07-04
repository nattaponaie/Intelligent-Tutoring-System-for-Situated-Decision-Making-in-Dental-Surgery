package com.surgical.decision3.common.controller;

import java.io.FileNotFoundException;
import java.io.InputStream;

import fr.uga.pddl4j.parser.Domain;
import fr.uga.pddl4j.parser.Parser;
import fr.uga.pddl4j.parser.Problem;

/**
 * Created by Nattapon on 4/6/2560.
 */

public class ParserController {

    public static Domain domain = null;
    public static Problem problem = null;


    public static Parser parse(InputStream domain, InputStream problem, InputStream procedure) throws FileNotFoundException
    {
        Parser parser = new Parser();
        try
        {
            parser.parse(domain, problem, procedure);

            if( parser.getErrorManager().isEmpty() )
            {
                System.out.println( "-- Parse OK --" );
            }
            else
            {
                System.out.println( "-- Parse NOT OK --" );
                parser.getErrorManager().printAll();
            }
        }
        catch (FileNotFoundException fnfException)
        {
            fnfException.printStackTrace();
            System.out.println( "-- domain or problem missing --" );
        }

        return parser;
    }

}
