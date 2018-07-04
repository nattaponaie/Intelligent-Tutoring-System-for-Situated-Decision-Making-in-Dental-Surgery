package kb.common.controller;

import android.support.v7.app.AppCompatActivity;

import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import fr.uga.pddl4j.parser.Domain;
import fr.uga.pddl4j.parser.NamedTypedList;
import fr.uga.pddl4j.parser.Parser;
import fr.uga.pddl4j.parser.Problem;
import fr.uga.pddl4j.parser.Symbol;
import fr.uga.pddl4j.parser.TypedSymbol;
import kb.common.datastructure.solutionpath.Plan;

/**
 * Created by Nattapon on 4/6/2560.
 */

public class ParserController extends AppCompatActivity {

    public static Domain domain = null;
    public static Problem problem = null;
    public static Plan plan = null;


    public static NamedTypedList getPredicateByBaseString(String baseString )
    {
        NamedTypedList predicate = null;

        for( NamedTypedList ntl: domain.getPredicates() )
        {
            if( ntl.getName().getImage().equals( baseString ) )
            {
                predicate = ntl;
                System.out.println( "--> BaseString: " + baseString );
            }
        }

        return predicate;
    }

    public static Symbol getTypeOfConstantByPredicate(NamedTypedList predicate )
    {
        //We can accept only one argument
        if( predicate.getArguments().size() != 1 )
        {
            return null;
        }

        Symbol typeOfConstant = null;

        TypedSymbol argumentType = predicate.getArguments().get(0);
        if(argumentType.getKind().equals(Symbol.Kind.VARIABLE) )
        {
            Symbol tempTypeSymbol = argumentType.getTypes().get(0);
            if(tempTypeSymbol.getKind().equals(Symbol.Kind.TYPE))
            {
                typeOfConstant = tempTypeSymbol;
            }
        }

        return typeOfConstant;
    }

    public static List<TypedSymbol> getConstantsByType( Symbol constantTypeParam )
    {
        if(!constantTypeParam.getKind().equals(Symbol.Kind.TYPE))
        {
            return null;
        }

        List<TypedSymbol> availableConstantsByTypeList = null;
        List<TypedSymbol> allDomainConstants = domain.getConstants();
        for( TypedSymbol ts: allDomainConstants )
        {
            if( ts.getKind().equals(Symbol.Kind.CONSTANT))
            {
                Symbol constantType = ts.getTypes().get(0);
                if( constantTypeParam.equals( constantType ) )
                {
                    if( availableConstantsByTypeList == null )
                    {
                        availableConstantsByTypeList = new ArrayList<TypedSymbol>();
                    }
                    availableConstantsByTypeList.add(ts);
                }
            }
        }

        return availableConstantsByTypeList;
    }

    public static Parser parse(InputStream domain, InputStream problem, InputStream plan) throws FileNotFoundException
    {
        Parser parser = new Parser();
        try
        {
            parser.parse(domain, problem, plan);

            if( parser.getErrorManager().isEmpty() )
            {
                //in android side the program always show Parse OK because
                //LOGGER dose not included in android (Parser.java)
                System.err.println( "-- Parse OK --" );
            }
            else
            {
                System.err.println( "-- Parse NOT OK --" );
                parser.getErrorManager().printAll();
            }
        }
        catch (FileNotFoundException fnfException)
        {
            fnfException.printStackTrace();
            System.err.println( "-- domain or problem missing --" );
        }

        return parser;
    }
}
