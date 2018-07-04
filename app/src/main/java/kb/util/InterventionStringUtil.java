package kb.util;

import com.surgical.decision3.common.controller.ApplicationController;
import com.surgical.decision3.common.controller.ParserController;

import java.util.ArrayList;
import java.util.List;

import fr.uga.pddl4j.parser.Connective;
import fr.uga.pddl4j.parser.Exp;
import fr.uga.pddl4j.parser.Symbol;
import fr.uga.pddl4j.parser.TypedSymbol;

/**
 * Created by Dell on 7/17/2017.
 */

public class InterventionStringUtil
{
    private static String getStringOfListItem( List<String> x_List)
    {
        if( x_List == null || x_List.size() == 0 )
        {
            return null;
        }

        String output = "";

        for( String str : x_List )
        {
            if( x_List.indexOf( str ) > 0 )
            {
                output += ",";
            }
            output += str;
        }

        return output;
    }

    public static List<String> getKeyListFromExpression(Exp expression, List<String> final_key_list,
                                                        Connective parentConnective)
    {
        List<String> key_list = null;

        //List<String> final_key_list = new ArrayList<>();

        if (final_key_list == null)
        {
            final_key_list = new ArrayList<>();
        }

        if (expression.getAtom() != null)
        {
            //String textFromMap = getTextFromAtom(expression.getAtom(), parentConnective);
            key_list = getKeyFromAtom(expression.getAtom(), parentConnective);

            if (key_list != null && key_list.size() > 0 )
            {
                //outputStringList.add(textFromMap);
                final_key_list.addAll(key_list);
            }
        }
        else
        {
            // 13/12/2016: Fix the connective NOT
            if (expression.getConnective().equals(Connective.NOT))
            {
                Exp child_e = expression.getChildren().get(0);

                //String textFromMap = getTextFromAtom(child_e.getAtom(), expression.getConnective());
                key_list = getKeyFromAtom(expression.getAtom(), parentConnective);

                if (key_list != null && key_list.size() > 0)
                {
                    //outputStringList.add(textFromMap);
                    final_key_list.addAll(key_list);
                }
            }
            else if (expression.getConnective().equals(Connective.LESS)
                    || expression.getConnective().equals(Connective.LESS_OR_EQUAL)
                    || expression.getConnective().equals(Connective.GREATER)
                    || expression.getConnective().equals(Connective.GREATER_OR_EQUAL))

            {
                //NOTE: this block is for manage the text e.g. ( < x y ) only.
                Connective conn = expression.getConnective();

                //String conn_text = TemplateUtil.i_predicate_function_map.get( conn.toString().toLowerCase() );
                String conn_text_key = conn.toString().toLowerCase();

                Exp first_exp = expression.getChildren().get( 0 );		//first_exp.connective = F_EXP
                //String first_text = getTextFromAtom( first_exp.getChildren().get(0).getAtom(), expression.getConnective() );
                List<String> first_text_key_list = getKeyFromAtom( first_exp.getChildren().get(0).getAtom(), expression.getConnective() );
                String first_text_key_string = getStringOfListItem(first_text_key_list);

                Exp second_exp = expression.getChildren().get( 1 );
                //String second_text = getTextFromAtom( second_exp.getChildren().get(0).getAtom(), expression.getConnective() );
                List<String> second_text_key_list = getKeyFromAtom( second_exp.getChildren().get(0).getAtom(), expression.getConnective() );
                String second_text_key_string = getStringOfListItem(second_text_key_list);


                String final_text = first_text_key_string;
                final_text += " " + conn_text_key;
                final_text += " " + second_text_key_string;

                final_key_list.add(final_text);

            }
            else if( expression.getConnective().equals(Connective.EQUAL)
                    || expression.getConnective().equals(Connective.FN_ATOM))	//NOTE: FN_ATOM is for (= x 10)
            {
                Connective conn = expression.getConnective();

                //String conn_text = TemplateUtil.i_predicate_function_map.get( conn.toString().toLowerCase() );
                //text_from_map = TemplateUtil.i_predicate_function_map.get(key.toLowerCase());
                String conn_text_key = conn.toString().toLowerCase();

                Exp first_exp = expression.getChildren().get( 0 );		//first_exp.connective = F_EXP
                //String first_text = getTextFromAtom( first_exp.getAtom(),  expression.getConnective() );
                List<String> first_text_key_list = getKeyFromAtom( first_exp.getChildren().get(0).getAtom(), expression.getConnective() );
                String first_text_key_string = getStringOfListItem(first_text_key_list);

                Exp second_exp = expression.getChildren().get( 1 );
                String second_text  = null;
                if( second_exp.getConnective().equals( Connective.NUMBER ) )
                {
                    second_text = second_exp.getValue().toString();
                }

                String final_text = first_text_key_string;
                final_text += " " + conn_text_key;
                final_text += " " + second_text;

                final_key_list.add(final_text);
            }
            else if (expression.getConnective().equals(Connective.AND)
                    || expression.getConnective().equals( Connective.F_EXP ))
            {
                // Get children
                List<Exp> exp_children = expression.getChildren();

                // Get list of output strength in array, and combine with the
                // root connective
                for (Exp e : exp_children)
                {
                    if (e.getConnective().equals(Connective.NOT))
                    {
                        Exp child_e = e.getChildren().get(0);

                        //String textFromMap = getTextFromAtom(child_e.getAtom(), e.getConnective());
                        key_list = getKeyFromAtom( child_e.getAtom(), e.getConnective() );

                        if (key_list != null && key_list.size() > 0)
                        {
                            //outputStringList.add(textFromMap);
                            final_key_list.addAll(key_list);
                        }
                    } else if (e.getConnective().equals(Connective.LESS)
                            || e.getConnective().equals(Connective.LESS_OR_EQUAL)
                            || e.getConnective().equals(Connective.GREATER)
                            || e.getConnective().equals(Connective.GREATER_OR_EQUAL)
                            || expression.getConnective().equals(Connective.EQUAL)
                            || expression.getConnective().equals(Connective.FN_ATOM) )
                    {
                        // YOU NEED TO IMPLEMENT HERE. IF THERE IS A CASE
                        final_key_list = getKeyListFromExpression(e, final_key_list, e.getConnective());
                    }
                    else
                    {
                        final_key_list = getKeyListFromExpression(e, final_key_list, e.getConnective());
                    }
                }
            }
        }

        return final_key_list;
    }


    //flatten and extract the key for getting english description from map
    public static List<String> transformExpression(Exp expression, List<String> outputStringList,
                                                   Connective parentConnective)
    {

        if (outputStringList == null)
        {
            outputStringList = new ArrayList<>();
        }

        String text_from_map = null;

        if (expression.getAtom() != null)
        {
            String textFromMap = getTextFromAtom(expression.getAtom(), parentConnective);

            if (textFromMap != null && textFromMap.trim().length() > 0)
            {
                outputStringList.add(textFromMap);
            }
        } else
        {

            // 13/12/2016: Fix the connective NOT
            if (expression.getConnective().equals(Connective.NOT))
            {
                Exp child_e = expression.getChildren().get(0);

                String textFromMap = getTextFromAtom(child_e.getAtom(), expression.getConnective());

                if (textFromMap != null && textFromMap.trim().length() > 0)
                {
                    outputStringList.add(textFromMap);
                }
            }
            else if (expression.getConnective().equals(Connective.LESS)
                    || expression.getConnective().equals(Connective.LESS_OR_EQUAL)
                    || expression.getConnective().equals(Connective.GREATER)
                    || expression.getConnective().equals(Connective.GREATER_OR_EQUAL))

            {
                //NOTE: this block is for manage the text e.g. ( < x y ) only.
                Connective conn = expression.getConnective();

                //String conn_text = TemplateUtil.i_predicate_function_map.get( conn.toString().toLowerCase() );
                String conn_text = ApplicationController.i_template_numeric_comparison_map.get( conn.toString().toLowerCase() );
                //text_from_map = TemplateUtil.i_predicate_function_map.get(key.toLowerCase());

                Exp first_exp = expression.getChildren().get( 0 );		//first_exp.connective = F_EXP
                String first_text = getTextFromAtom( first_exp.getChildren().get(0).getAtom(),
                        expression.getConnective() );

                Exp second_exp = expression.getChildren().get( 1 );
                String second_text = getTextFromAtom( second_exp.getChildren().get(0).getAtom(),
                        expression.getConnective() );

                String final_text = first_text;
                final_text += " " + conn_text;
                final_text += " " + second_text;

                outputStringList.add(final_text);

            }
            else if( expression.getConnective().equals(Connective.EQUAL)
                    || expression.getConnective().equals(Connective.FN_ATOM))	//NOTE: FN_ATOM is for (= x 10)
            {
                Connective conn = expression.getConnective();

                String conn_text = ApplicationController.i_template_numeric_comparison_map.get( conn.toString().toLowerCase() );
                //String conn_text = TemplateUtil.i_predicate_function_map.get( conn.toString().toLowerCase() );
                //text_from_map = TemplateUtil.i_predicate_function_map.get(key.toLowerCase());

                Exp first_exp = expression.getChildren().get( 0 );		//first_exp.connective = F_EXP
                String first_text = null;

                if( first_exp.getAtom() != null )
                {
                    first_text = getTextFromAtom( first_exp.getAtom(), expression.getConnective() );
                }
                else
                {
                    first_text = getTextFromAtom( first_exp.getChildren().get(0).getAtom(), expression.getConnective() );
                }

                Exp second_exp = expression.getChildren().get( 1 );
                String second_text  = null;
                if( second_exp.getConnective().equals( Connective.NUMBER ) )
                {
                    second_text = second_exp.getValue().toString();
                }
                else
                {
                    //Nui add
                    if( second_exp.getAtom() != null )
                    {
                        second_text = getTextFromAtom( second_exp.getAtom(), expression.getConnective() );
                    }
                    else
                    {
                        second_text = getTextFromAtom( second_exp.getChildren().get(0).getAtom(), expression.getConnective() );
                    }
                }

                String final_text = first_text;
                final_text += " " + conn_text;
                final_text += " " + second_text;

                outputStringList.add(final_text);
            }
            else if (expression.getConnective().equals(Connective.AND)
                    || expression.getConnective().equals( Connective.F_EXP ))
            {
                // Get children
                List<Exp> exp_children = expression.getChildren();

                // Get list of output strength in array, and combine with the
                // root connective
                for (Exp e : exp_children)
                {
                    if (e.getConnective().equals(Connective.NOT))
                    {
                        Exp child_e = e.getChildren().get(0);

                        String textFromMap = getTextFromAtom(child_e.getAtom(), e.getConnective());

                        if (textFromMap != null && textFromMap.trim().length() > 0)
                        {
                            outputStringList.add(textFromMap);
                        }
                    } else if (e.getConnective().equals(Connective.LESS)
                            || e.getConnective().equals(Connective.LESS_OR_EQUAL)
                            || e.getConnective().equals(Connective.GREATER)
                            || e.getConnective().equals(Connective.GREATER_OR_EQUAL)
                            || expression.getConnective().equals(Connective.EQUAL)
                            || expression.getConnective().equals(Connective.FN_ATOM) )
                    {
                        // YOU NEED TO IMPLEMENT HERE. IF THERE IS A CASE
                        outputStringList = transformExpression(e, outputStringList, e.getConnective());
                    }
                    else
                    {
                        outputStringList = transformExpression(e, outputStringList, e.getConnective());
                    }
                }
            }
        }

        return outputStringList;
    }

    private static List<String> getKeyFromAtom(List<Symbol> atomList, Connective parentConnective)
    {
        boolean isParentConnectiveNegation = parentConnective.equals(Connective.NOT);

        List<String> key_list = new ArrayList<>();

        //String text_from_map = null;

        for (Symbol s : atomList)
        {
            // The first symbol is predicate.
            if (atomList.indexOf(s) == 0)
            {
                String key = s.getImage();
                if (isParentConnectiveNegation)
                {
                    key += "_" + Connective.NOT.toString();
                }

                key_list.add(key);

                //text_from_map = TemplateUtil.i_predicate_function_map.get(key.toLowerCase());
            }
            else if (atomList.indexOf(s) > 0)
            {
				/*
				// if it is constant, search
				// if it is variable and it is in constants, get it.
				if (s.getKind().equals(Kind.CONSTANT))
				{
					// text_from_map += " " + i_predicate_function_map.get(
					// parentConnective.toString().toLowerCase() ) + " ";
					//text_from_map += " " + TemplateUtil.i_predicate_function_map.get(s.getImage().toLowerCase());
				} else if (s.getKind().equals(Kind.VARIABLE))
				{
					if (isInDomainConstant(s.getImage()))
					{
						// text_from_map += " " + i_predicate_function_map.get(
						// parentConnective.toString().toLowerCase() ) + " ";
						//text_from_map += " " + TemplateUtil.i_predicate_function_map.get(s.getImage().toLowerCase());
					}
					else
					{
						System.out.println("--> NOTHING " + s);
					}
				}
			*/
            }
        }

        return key_list;
    }

    private static String getTextFromAtom_only1stPredicate(List<Symbol> atomList, Connective parentConnective)
    {
        boolean isParentConnectiveNegation = parentConnective.equals(Connective.NOT);

        String text_from_map = null;

        for (Symbol s : atomList)
        {
            // The first symbol is predicate.
            if (atomList.indexOf(s) == 0)
            {
                String key = s.getImage();
                if (isParentConnectiveNegation)
                {
                    key += "_" + Connective.NOT.toString();
                }

                text_from_map = ApplicationController.domain_predicate_map.get(key.toLowerCase());
            }
//            else if (atomList.indexOf(s) > 0)
//            {
//                // if it is constant, search
//                // if it is variable and it is in constants, get it.
//                if (s.getKind().equals(Symbol.Kind.CONSTANT))
//                {
//                    // text_from_map += " " + i_predicate_function_map.get(
//                    // parentConnective.toString().toLowerCase() ) + " ";
//                    text_from_map += " " + ApplicationController.domain_predicate_map.get(s.getImage().toLowerCase());
//                } else if (s.getKind().equals(Symbol.Kind.VARIABLE))
//                {
//                    if (isInDomainConstant(s.getImage()))
//                    {
//                        // text_from_map += " " + i_predicate_function_map.get(
//                        // parentConnective.toString().toLowerCase() ) + " ";
//                        text_from_map += " " + ApplicationController.domain_predicate_map.get(s.getImage().toLowerCase());
//                    } else
//                    {
//                        System.out.println("--> NOTHING " + s);
//                    }
//                }
//            }
        }

        return text_from_map;
    }

    private static String getTextFromAtom(List<Symbol> atomList, Connective parentConnective)
    {
        boolean isParentConnectiveNegation = parentConnective.equals(Connective.NOT);

        String text_from_map = null;

        for (Symbol s : atomList)
        {
            // The first symbol is predicate.
            if (atomList.indexOf(s) == 0)
            {
                String key = s.getImage();
                if (isParentConnectiveNegation)
                {
                    key += "_" + Connective.NOT.toString();
                }

                text_from_map = ApplicationController.domain_predicate_map.get(key.toLowerCase());
            } else if (atomList.indexOf(s) > 0)
            {
                // if it is constant, search
                // if it is variable and it is in constants, get it.
                if (s.getKind().equals(Symbol.Kind.CONSTANT))
                {
                    // text_from_map += " " + i_predicate_function_map.get(
                    // parentConnective.toString().toLowerCase() ) + " ";
                    text_from_map += " " + ApplicationController.domain_predicate_map.get(s.getImage().toLowerCase());
                } else if (s.getKind().equals(Symbol.Kind.VARIABLE))
                {
                    if (isInDomainConstant(s.getImage()))
                    {
                        // text_from_map += " " + i_predicate_function_map.get(
                        // parentConnective.toString().toLowerCase() ) + " ";
                        text_from_map += " " + ApplicationController.domain_predicate_map.get(s.getImage().toLowerCase());
                    } else
                    {
                        System.out.println("--> NOTHING " + s);
                    }
                }
            }
        }

        return text_from_map;
    }

    //NOTE: THE ORIGINAL CODE IS FROM TEMPLATEUTIL class. I haven't made the decision about final version yet.
    private static boolean isInDomainConstant(String in)
    {
        boolean isConstant = false;

        List<TypedSymbol> domainConstants = ParserController.domain.getConstants();   //ParserUtil.domain.getConstants();
        if (domainConstants != null && domainConstants.size() > 0)
        {
            for (TypedSymbol t : domainConstants)
            {
                if (t.getImage().equals(in))
                {
                    isConstant = true;
                    break;
                }
            }
        }

        return isConstant;
    }

    public static String generateFinalString(List<String> stringList, Connective conn)
    {
        String message = "";

        for (String str : stringList)
        {
            //if str is the first item
            if (stringList.indexOf(str) == 0)
            {
                message += str;
            }

            if (stringList.size() >= 2 && stringList.indexOf(str) > 0)
            {
                //if str is the last item
                if (stringList.indexOf(str) == (stringList.size() - 1))
                {
                    if (stringList.size() == 2)
                    {
                        message += " and " + str;
                    }
                    else
                    {
                        message += ", and " + str;
                    }
                }
                else
                {
                    //this is not last item,
                    message += ", " + str;
                }
            }
        }
        return message;
    }

    public static String transformExpToFinalText( Exp exp )
    {
        List<String> outputStringList = new ArrayList<>();
        outputStringList = InterventionStringUtil.transformExpression( exp, outputStringList , exp.getConnective() );
        String final_string = InterventionStringUtil.generateFinalString(outputStringList, Connective.AND );

        //TODO: do not forget to remove '\n' from the string

        return final_string;
    }

    //Note: Get the first predicate string from
    // input: (rb_pos_nose cover)  ==> rb_pos_nose
    // input: (not(rb_pos_nose cover))  ==> rb_pos_nose_not
    public static String get1stPredicateStringFromExp( Exp exp )
    {
        String predicateString = null;
        Symbol predicateSymbol = null;

        if( exp.getConnective().equals(Connective.AND ) )
        {
            //TODO: If the connective is AND, the exp must be flattened, and get the list of String to be predicated.

            /*
            * IDEA: outputStringList = InterventionStringUtil.transformExpression( exp, outputStringList , exp.getConnective() );
        String final_string = InterventionStringUtil.generateFinalString(outputStringList, Connective.AND );
            * */
        }
        else if( exp.getConnective().equals(Connective.NOT ) )
        {
            Exp tempExp = exp.getChildren().get(0);
            predicateString = get1stPredicateStringFromExp( tempExp );
            predicateString = predicateString.concat( "_not" );
        }
        else
        {

            Symbol symbol = exp.getAtom().get(0);

            if( symbol.getKind().equals(Symbol.Kind.PREDICATE ))
            {
                predicateSymbol = symbol;
                predicateString = predicateSymbol.getImage();
            }
        }
        return predicateString;
    }

//    public static String extractTheBasePredicateFromExpList( List<Exp> expList )
//    {
//        String predicateString = null;
//
//        for( Exp e : expList )
//        {
//            String eString = getTheBasePredicateFromExp( e );
//
//            if( eString != null )
//            {
//                outputStringList.add( eString );
//            }
//        }
//
//        return predicateString;
//    }

    public static String getTheBasePredicateValueFromExp( Exp exp )
    {
        String predicateValueString = null;
        if( exp.getConnective().equals(Connective.NOT ) )
        {
            Exp tempExp = exp.getChildren().get( 0 );
            predicateValueString = getTheBasePredicateValueFromExp( tempExp );
        }
        else if( exp.getConnective().equals(Connective.AND ) )
        {
            predicateValueString = null;
        }
        else
        {

            Symbol symbol = exp.getAtom().get(1);

            if( symbol.getKind().equals(Symbol.Kind.CONSTANT ))
            {
                Symbol predicateValueSymbol = symbol;
                predicateValueString = predicateValueSymbol.getImage();
            }
        }

        return predicateValueString;
    }

    //Note: Get the first predicate string from
    // input: (rb_pos_nose cover)  ==> rb_pos_nose
    // input: (not(rb_pos_nose cover))  ==> rb_pos_nose
    public static String getTheBasePredicateFromExp( Exp exp )
    {
        String predicateString = null;
        Symbol predicateSymbol = null;
        if( exp.getConnective().equals(Connective.NOT ) )
        {
            Exp tempExp = exp.getChildren().get( 0 );
            predicateString = getTheBasePredicateFromExp( tempExp );
        }
        else if( exp.getConnective().equals(Connective.AND ) )
        {
            predicateString = null;
        }
        else
        {

            Symbol symbol = exp.getAtom().get(0);

            if( symbol.getKind().equals(Symbol.Kind.PREDICATE ))
            {
                predicateSymbol = symbol;
                predicateString = predicateSymbol.getImage();
            }
        }

        return predicateString;
    }

//    public static List<String> extractTheBaseStringListFromExpList( List<Exp> inExpList, List<String> outputStringList )
//    {
//        if( outputStringList == null )
//        {
//            outputStringList = new ArrayList<>();
//        }
//
//        outputStringList = getTheBaseStringListPredicateFromExp( inExpList, outputStringList );
//
//        return outputStringList;
//    }

//    public static List<String> getTheBaseStringListPredicateFromExp( List<Exp> inExpList, List<String> outputStringList )
//    {
//        if( outputStringList == null )
//        {
//            outputStringList = new ArrayList<>();
//        }
//
//        List<Exp> expList = flattenExpList(inExpList, null );
//
//        List<String> basePredicateExpStringList = new ArrayList<>();
//
//        for( Exp e : expList )
//        {
//            String eString = getTheBasePredicateFromExp( e );
//
//            if( eString != null )
//            {
//                outputStringList.add( eString );
//            }
//        }
//
//        return outputStringList;
//    }

    public static List<Exp> flattenExpList( List<Exp> inExpList, List<Exp> outputExpList )
    {
        if( outputExpList == null )
        {
            outputExpList = new ArrayList<>();
        }

        for( Exp inExp : inExpList )
        {
            outputExpList = flattenExpList( inExp, outputExpList );
        }

        return outputExpList;
    }

    public static Exp extractMainProjectionExp( Exp answerPjExp )
    {
        if( InferenceEngineUtil.isCompoundPredicate( answerPjExp ) )
        {
            Exp newMainExp = new Exp(Connective.AND);

            List<Exp> tempList = flattenExpList( answerPjExp, null );
            for( Exp e : tempList )
            {
                if( e.isMain() )
                {
                    newMainExp.addChild( e );
                }
            }

            //clean newMainExp (if the connective is AND with only one item, clean it)
            if( newMainExp.getChildren().size() > 0 && newMainExp.getChildren().size() == 1 )
            {
                newMainExp = newMainExp.getChildren().get( 0 );
            }
            else if( newMainExp.getChildren().size() == 0 )
            {
                return answerPjExp;
            }

            return newMainExp;
        }
        else
        {
            return answerPjExp;
        }
    }

    public static List<Exp> flattenExpList( Exp effect, List<Exp> outputExpList)
    {
        if( outputExpList == null )
        {
            outputExpList = new ArrayList<>();
        }

        if( effect.getConnective().equals( Connective.NOT )
                || effect.getConnective().equals( Connective.ATOM )
//			|| effect.getConnective().equals( Connective.LESS )
//			|| effect.getConnective().equals( Connective.LESS_OR_EQUAL)
//			|| effect.getConnective().equals( Connective.GREATER)
//			|| effect.getConnective().equals( Connective.GREATER_OR_EQUAL )
//			|| effect.getConnective().equals( Connective.EQUAL)
                )
        {
            if( ! outputExpList.contains( effect ) )
            {
                outputExpList.add(effect);
            }
        }
        //else if( effect.getChildren().size() > 0 )
        else if( effect.getConnective().equals( Connective.AND ) && ( effect.getChildren().size() > 0 ) )
        {
            for(Exp eff: effect.getChildren())
            {
                //effectList.add( eff.getAtom().get(0));
                outputExpList = flattenExpList( eff, outputExpList);
            }
        }

        return outputExpList;
    }

    public static Exp getExpFromListByKeyBaseExp( List<Exp> expList, Exp keyExp )
    {
        Exp targetExp = null;

        if(keyExp == null )
        {
            return null;
        }

        if( expList == null || expList.size() == 0 )
        {
            return null;
        }

        String base_key = getTheBasePredicateFromExp( keyExp );

        for( Exp e: expList )
        {
            String base_e = getTheBasePredicateFromExp( e );
            if( base_e.trim().toLowerCase().equals( base_key.trim().toLowerCase() ) )
            {
                targetExp = e;
                break;
            }
        }

        return targetExp;

    }

}
