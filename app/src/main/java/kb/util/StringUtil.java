package kb.util;

import java.util.ArrayList;
import java.util.List;

import fr.uga.pddl4j.parser.Connective;
import fr.uga.pddl4j.parser.Exp;
import fr.uga.pddl4j.parser.Symbol;
import kb.common.datastructure.graph.StudentGraphNode;

/**
 * Created by Nattapon on 21/6/2560.
 */

public class StringUtil {

    public static String getProjectionEffectStringFromProjectionNode()
    {
        String projectionEffectsString = "";
        String newLine = "\r\n";

        for (StudentGraphNode each_projection : StudentGraphUtil.getCurrentStudentGraph().getProjectionList()) {

            String projectionString = InterventionStringUtil.transformExpToFinalText( each_projection.getProjectionExp());

            //TODO some projection effect is not in the domain_predicate.txt

            if(!projectionString.equals(""))
            {
                projectionEffectsString =
                        projectionEffectsString.concat( InterventionStringUtil.transformExpToFinalText( each_projection.getProjectionExp() ));
                projectionEffectsString = projectionEffectsString.concat(newLine);
            }
        }

        return projectionEffectsString;
    }

    public static ArrayList<String> getSubStringFromExpList(Exp exp)
    {
        ArrayList<String> predicate_string = new ArrayList<>();
        String string = "";

        //if this predicate has Connective NOT
        if(exp.getConnective().equals(Connective.NOT))
        {
            //this predicate must not have constant value
            if(exp.getChildren().get(0).getAtom().size() < 2) {
//                System.out.println("toey not " + exp.getChildren().get(0).getAtom().get(0));

                string = exp.getChildren().get(0).getAtom().get(0).toString();
                string = string + "_not";
                predicate_string.add(string);
            }
            else {
                string = exp.getChildren().get(0).getAtom().get(0).toString();
//                string = exp.getChildren().get(0).toString().substring(1, exp.getChildren().get(0).toString().length()-1);
                string = string + "_not";
                if(!string.substring(0, 1).matches("[0-9]")) {
                    predicate_string.add(string);
                }
            }
        }
        //if this predicate is Function
        else if(exp.getConnective().equals(Connective.EQUAL)
                || exp.getConnective().equals(Connective.FN_ATOM))
        {
            string = exp.getChildren().get(0).toString().substring(1, exp.getChildren().get(0).toString().length()-1);
            if(!string.substring(0, 1).matches("[0-9]")) {
                predicate_string.add(string);
            }
        }
        //if this predicate is Function
        else if(exp.getConnective().equals(Connective.LESS)
                || exp.getConnective().equals(Connective.LESS_OR_EQUAL)
                || exp.getConnective().equals(Connective.GREATER)
                || exp.getConnective().equals(Connective.GREATER_OR_EQUAL))
        {
//            System.out.println("toey func "+exp.getChildren().get(0).toString());
//            System.out.println("toey func "+exp.getChildren().get(1).toString());

            string = exp.getChildren().get(0).toString().substring(1, exp.getChildren().get(0).toString().length()-1);
            if(!string.substring(0, 1).matches("[0-9]")) {
                predicate_string.add(string);
            }
            string = exp.getChildren().get(1).toString().substring(1, exp.getChildren().get(1).toString().length()-1);
            if(!string.substring(0, 1).matches("[0-9]")) {
                predicate_string.add(string);
            }
        }
        //if this predicate has Constant
        else if(exp.getAtom() != null)
        {
//            System.out.println("toey const "+exp.getAtom().get(0).toString());
            string = exp.getAtom().get(0).toString();
            if(!string.substring(0, 1).matches("[0-9]")) {
                predicate_string.add(string);
            }
        }
        //another cases
        else
        {
//            System.out.println("toey normal "+exp.toString());

            string = exp.toString().substring(1, exp.toString().length()-1);
            if(!string.substring(0, 1).matches("[0-9]")) {
                predicate_string.add(string);
            }
        }

        return predicate_string;
    }

    //Get predicate
    public static String subStringExp(Exp expValue)
    {
        String string = "";
        if(expValue.getConnective().equals(Connective.NOT)) {

            //if this predicate has constant value
            if(expValue.getChildren().get(0).getAtom().size() < 2) {
//                System.out.println("toey not " + exp.getChildren().get(0).getAtom().get(0));

                string = expValue.getChildren().get(0).getAtom().get(0).toString();
                string = string + "_not";
            }
            else {
                string = expValue.toString().substring(6, expValue.toString().length() - 2);
                string = string + "_not";
            }
        }
        else
        {
            //if this predicate has constant value
            if(expValue.getAtom() != null)
            {
                if(expValue.getAtom().size() < 2) {
                    string = expValue.getAtom().get(0).toString();
                }
                else string = expValue.toString().substring(1, expValue.toString().length()-1);
            }
        }

        return string;
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

    //output x,y,z
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



}
