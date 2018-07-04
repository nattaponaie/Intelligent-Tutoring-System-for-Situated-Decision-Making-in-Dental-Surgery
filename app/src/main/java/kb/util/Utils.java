package kb.util;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

import fr.uga.pddl4j.parser.Connective;
import fr.uga.pddl4j.parser.Exp;

import static com.surgical.decision3.common.controller.ApplicationController.action_predicate_map;
import static kb.util.WorldStateUtil.getCurrentState;

/**
 * Created by Nattapon on 13/6/2560.
 */

public class Utils {

    public static List<Exp> projectionExpList = new ArrayList<>();

    public static boolean isAvailableInProjectionEffect(Exp each_condition_exp )
    {
        boolean isAvailable = false;

        boolean isExistInUserAction = isThisPredicateExistInUserAction(each_condition_exp);
        if(isExistInUserAction)//if this condition is user action predicate
        {
            for( Exp ex_item: projectionExpList)
            {
                boolean same = areBothPredicateHaveSameMeaning(ex_item, each_condition_exp);
                if (same) {
                    isAvailable = true;
                    break;
                }
                else if( each_condition_exp.equals( ex_item ) )
                {
                    isAvailable = true;
                    break;
                }
            }
        }
        else//if this condition is not user action predicate
        {
            for( Exp ex_item: projectionExpList)
            {
                if( each_condition_exp.equals( ex_item ) )
                {
                    isAvailable = true;
                    break;
                }
            }
        }

        return isAvailable;
    }

    public static boolean isAvailableInWhenCase(List<Exp> actionPredicateFactList, Exp each_condition_exp )
    {
        String conditionString = InferenceEngineUtil.getAtomStringFromPredicate(each_condition_exp);

        for(Exp exp_item : actionPredicateFactList )
        {
            String factString = InferenceEngineUtil.getAtomStringFromPredicate(exp_item);

            if(conditionString.equals(factString))
            {
                //If this fact has NOT Connective
                if(each_condition_exp.getConnective().equals(Connective.NOT))
                {
                    //if this condition has value
                    if(each_condition_exp.getChildren().get(0).getAtom().size() == 2)
                    {
                        String conditionSecondAtom = InferenceEngineUtil.getSecondAtomStringFromPredicate(each_condition_exp);
                        String effectSecondAtom = InferenceEngineUtil.getSecondAtomStringFromPredicate(exp_item);

                        if(!effectSecondAtom.equals(conditionSecondAtom)) return true;
                    }
                }
            }
        }

        return false;
    }

    public static boolean isAvailable(List<Exp> actionPredicateFactList, Exp each_condition_exp )
    {
        boolean isAvailable = false;

        boolean isExistInUserAction = isThisPredicateExistInUserAction(each_condition_exp);
        if(isExistInUserAction)//if this condition is user action predicate
        {
            boolean isExistInWorldState = isThisPredicateExistInWorldState(actionPredicateFactList, each_condition_exp);
            if(!isExistInWorldState)//if this condition exist in world state
            {
                if(each_condition_exp.getConnective().equals(Connective.NOT))
                {
                    isAvailable = true;
                }
            }
            else
            {
                for( Exp ex_item: actionPredicateFactList)
                {
                    boolean same = areBothPredicateHaveSameMeaning(ex_item, each_condition_exp);
                    if (same) {
                        isAvailable = true;
                        break;
                    }
                    else if( each_condition_exp.equals( ex_item ) )
                    {
                        isAvailable = true;
                        break;
                    }
                }
            }
        }
        else//if this condition is not user action predicate
        {
            for( Exp ex_item: actionPredicateFactList)
            {
                if( each_condition_exp.equals( ex_item ) )
                {
                    isAvailable = true;
                    break;
                }
            }
        }

        return isAvailable;
    }

    private static boolean isThisPredicateExistInWorldState(List<Exp> expList, Exp exp)
    {
        for( Exp ex_item: expList)
        {
            String ex_item_string = getPredicateStringFromExp(ex_item);
            String exp_string = getPredicateStringFromExp(exp);

            if( ex_item_string.equals( exp_string ) )
            {
                return true;
            }
        }

        return false;
    }

    private static boolean isThisPredicateExistInUserAction(Exp predicateExp)
    {
        //the predicate must be user action predicate
        String predicateString = getPredicateStringFromExp(predicateExp);

        for (Map.Entry<String, String> action_pred_map : action_predicate_map.entrySet()) {
            if(action_pred_map.getValue().equals(predicateString))
            {
                return true;
            }
        }

        return false;
    }


    private static boolean areBothPredicateHaveSameMeaning(Exp ex_item, Exp exp)
    {
        //if this condition exp is Not Connective
        if(exp.getChildren().size() != 0 && exp.getConnective().equals(Connective.NOT))
        {
            //if this condition exp has constant value
            if(exp.getChildren().get(0).getAtom().size() == 2)
            {
                String ex_item_string = getPredicateStringFromExp(ex_item);
                String exp_string = getPredicateStringFromExp(exp.getChildren().get(0));

                if(ex_item_string.equals(exp_string))
                {
                    String constant_string_ex_item = getConstantStringValueFromExp(ex_item);
                    String constant_string_exp = getConstantStringValueFromExp(exp.getChildren().get(0));

//                    System.err.println("ex_item " +ex_item_string +" "+ constant_string_ex_item);
//                    System.err.println("exp " +exp_string +" "+ constant_string_exp);

                    if(!constant_string_ex_item.equals(constant_string_exp))
                    {
                        return true;
                    }
                }
            }
        }

        return false;
    }

    private static String getConstantStringValueFromExp(Exp exp)
    {
        String constantValue = "";

        //if this predicate has Connective NOT
        if(exp.getConnective().equals(Connective.NOT))
        {
            //do nothing
        }
        //if this predicate is Function
        else if(exp.getConnective().equals(Connective.EQUAL)
                || exp.getConnective().equals(Connective.FN_ATOM))
        {
            //do nothing
        }
        //if this predicate is Function
        else if(exp.getConnective().equals(Connective.LESS)
                || exp.getConnective().equals(Connective.LESS_OR_EQUAL)
                || exp.getConnective().equals(Connective.GREATER)
                || exp.getConnective().equals(Connective.GREATER_OR_EQUAL))
        {
            //do nothing
        }
        //if this predicate has Constant
        else if(exp.getAtom().size() == 2)
        {
            constantValue = exp.getAtom().get(1).toString();
        }
        //another cases
        else
        {
            //do nothing
        }

        return constantValue;
    }

    public static String getPredicateStringFromExp(Exp exp)
    {
        String string = "";

        //if this predicate has Connective NOT
        if(exp.getConnective().equals(Connective.NOT))
        {
            //if this predicate is not constant
//            if(exp.getChildren().get(0).getAtom().size() < 2) {
//
//                string = exp.getChildren().get(0).getAtom().get(0).toString();
//            }
//            else
//            {
//                string = exp.getChildren().get(0).getAtom().get(0).toString();
//            }
            string = exp.getChildren().get(0).getAtom().get(0).toString();
        }
        //if this predicate is Function
        else if(exp.getConnective().equals(Connective.EQUAL)
                || exp.getConnective().equals(Connective.FN_ATOM))
        {
            //do nothing
        }
        //if this predicate is Function
        else if(exp.getConnective().equals(Connective.LESS)
                || exp.getConnective().equals(Connective.LESS_OR_EQUAL)
                || exp.getConnective().equals(Connective.GREATER)
                || exp.getConnective().equals(Connective.GREATER_OR_EQUAL))
        {
            //do nothing
        }
        //if this predicate has Constant
        else if(exp.getAtom() != null)
        {
            string = exp.getAtom().get(0).toString();
        }
        //another cases
        else
        {
            string = exp.toString().substring(1, exp.toString().length()-1);
        }

        return string;
    }

    public static String getPredicateString(Exp exp)
    {
        String predicate_string = "";

        //if this predicate has Connective NOT
        if(exp.getConnective().equals(Connective.NOT))
        {
            //this predicate must not have constant value
            if(exp.getChildren().get(0).getAtom().size() < 2) {
//                System.out.println("toey not " + exp.getChildren().get(0).getAtom().get(0));

                predicate_string = exp.getChildren().get(0).getAtom().get(0).toString();
            }
            else {
                predicate_string = exp.getChildren().get(0).getAtom().get(0).toString();
            }
        }
        //if this predicate is Function
        else if(exp.getConnective().equals(Connective.EQUAL)
                || exp.getConnective().equals(Connective.FN_ATOM)
                || exp.getConnective().equals(Connective.LESS)
                || exp.getConnective().equals(Connective.LESS_OR_EQUAL)
                || exp.getConnective().equals(Connective.GREATER)
                || exp.getConnective().equals(Connective.GREATER_OR_EQUAL))
        {
            predicate_string = exp.getChildren().get(0).toString().substring(1, exp.getChildren().get(0).toString().length()-1);
        }
        //if this predicate has Constant
        else if(exp.getAtom() != null)
        {
            predicate_string = exp.getAtom().get(0).toString();
        }
        //another cases
        else
        {
            predicate_string = exp.toString().substring(1, exp.toString().length()-1);
        }


        return predicate_string;
    }

    public static boolean isAvailable_NumericComparison( List<Exp> expList, Exp exp, HashMap<Exp, Double> functionWithValueHashMap )
    {
        boolean isAvailable = false;

        for( Exp ex_item: expList)
        {
//            isAvailable = compareStringAndValue(ex_item, exp);
            isAvailable = compareConditionValue(exp, functionWithValueHashMap);
            if( isAvailable ) break;
        }
        return isAvailable;
    }

    public static String getExpName (Exp predicate)
    {
        String exp_name = "";

        if(predicate.getConnective().equals(Connective.EQUAL))
        {
            exp_name = predicate.getChildren().get(0).getChildren().get(0).getAtom().get(0).toString();
        }
        else if(predicate.getChildren().size() != 0)
        {
            if(predicate.getChildren().get(0).getAtom() != null)
            {
                exp_name = predicate.getChildren().get(0).getAtom().get(0).toString();
            }
            else
            {
                exp_name = predicate.getChildren().get(0).getChildren().get(0).toString();
            }
//            for (Exp predicateChild : predicate.getChildren()) {
//                if(predicateChild.getAtom().size() != 0)
//                {
//                    exp_name = predicateChild.getAtom().get(0).toString();
//                }
//            }
        }
        else
        {
            if(predicate.getAtom().size() != 0)
            {
                exp_name = predicate.getAtom().get(0).toString();
            }
        }

        return exp_name;
    }

    public static String getValueFromExp (Exp predicate)
    {
        String exp_name = "";

        if(predicate.getChildren().size() != 0) {
                exp_name = predicate.getChildren().get(1).toString();
        }
        else
        {
            exp_name = predicate.getAtom().get(1).toString();
        }

        return exp_name;
    }

    public static Exp getExpFromFactList(String expName)
    {
        Exp exp_return = null;

        for (Exp eachFactAndEffectExp : getCurrentState().getFactPredicate()) {
            String eachFactAndEffectExp_Name = getExpName(eachFactAndEffectExp);

            if(expName.equals(eachFactAndEffectExp_Name))
            {
                return eachFactAndEffectExp;
            }

        }

        return exp_return;
    }

    public static Exp getExpFromFactAndActList(String expName)
    {
        Exp exp_return = null;

        for (Exp eachFactAndEffectExp : WorldStateUtil.getCurrentFactAndActionPredicate()) {
            String eachFactAndEffectExp_Name = getExpName(eachFactAndEffectExp);

            if(expName.equals(eachFactAndEffectExp_Name))
            {
                return eachFactAndEffectExp;
            }

        }

        return exp_return;
    }

    public static Double getDoubleValueFromExp(Exp effectExp, HashMap<Exp, Double> functionWithValueHashMap)
    {
        Double value = 0.0;

        String conditionName = getExpName(effectExp);

        if(!functionWithValueHashMap.isEmpty())
        {
            for (Map.Entry<Exp, Double> each_function : functionWithValueHashMap.entrySet())
            {
                String functionString = getExpName(each_function.getKey());
                if(conditionName.equals(functionString))
                {
                    value = each_function.getValue();
                }
            }
        }

        if(value == 0.0) {

            Exp firstConditionExp = getExpFromFactList(conditionName);
            if (firstConditionExp == null) {
                firstConditionExp = getExpFromFactAndActList(conditionName);
            }
            String conditionValueString = getValueFromExp(firstConditionExp);
            if(!conditionValueString.equals("")) value = Double.parseDouble(conditionValueString);
        }

        return value;
    }

    private static boolean compareConditionValue(Exp conditionExp, HashMap<Exp, Double> functionWithValueHashMap)
    {
        String firstConditionName = getExpName(conditionExp.getChildren().get(0));
        String firstConditionValue = "";
        String secondConditionName = "";
        String secondConditionValue = "";

        //check if there is function value in the processing step
        if(!functionWithValueHashMap.isEmpty())
        {
            for (Map.Entry<Exp, Double> each_function : functionWithValueHashMap.entrySet())
            {
                String functionString = getExpName(each_function.getKey());
                if(firstConditionName.equals(functionString))
                {
                    firstConditionValue = each_function.getValue().toString();
                }
                if(secondConditionName.equals(functionString))
                {
                    secondConditionValue = each_function.getValue().toString();
                }
            }
        }

        //if firstConditionValue does not found
        if(firstConditionValue.equals("")) {

            Exp firstConditionExp = getExpFromFactList(firstConditionName);
            if (firstConditionExp == null) {
                firstConditionExp = getExpFromFactAndActList(firstConditionName);
                if (firstConditionExp == null) {
                    firstConditionExp = conditionExp.getChildren().get(0);
                }
            }
            firstConditionValue = getValueFromExp(firstConditionExp);
        }

        //in case that the condition is number
        if(conditionExp.getChildren().get(1).getChildren().get(0).getConnective().equals(Connective.NUMBER))
        {
            secondConditionValue = conditionExp.getChildren().get(1).toString();
        }
        else {
            if(secondConditionValue.equals("")) {
                secondConditionName = getExpName(conditionExp.getChildren().get(1));
                Exp secondConditionExp = getExpFromFactList(secondConditionName);
                if (secondConditionExp == null) {
                    secondConditionExp = getExpFromFactAndActList(secondConditionName);
                }
                secondConditionValue = getValueFromExp(secondConditionExp);
            }
        }

        Double firstConditionValueDouble = Double.parseDouble(firstConditionValue);
        Double secondConditionValueDouble = Double.parseDouble(secondConditionValue);

        if( conditionExp.getConnective().equals( Connective.LESS ))
        {
            if(firstConditionValueDouble < secondConditionValueDouble) return true;
        }
        else if( conditionExp.getConnective().equals( Connective.LESS_OR_EQUAL ))
        {
            if(firstConditionValueDouble <= secondConditionValueDouble) return true;
        }
        else if( conditionExp.getConnective().equals( Connective.GREATER ))
        {
            if(firstConditionValueDouble > secondConditionValueDouble) return true;
        }
        else if( conditionExp.getConnective().equals( Connective.GREATER_OR_EQUAL ))
        {
            if(firstConditionValueDouble >= secondConditionValueDouble) return true;
        }
        else if( conditionExp.getConnective().equals( Connective.EQUAL ))
        {
            if(firstConditionValueDouble.equals(secondConditionValueDouble)) return true;
        }

        return false;
    }

    public static boolean compareStringAndValue(Exp factAndActionExp, Exp conditionExp)
    {
        String FactAndActionPredicateString = getPredicateString(factAndActionExp);
        String conditionString = getPredicateString(conditionExp);

        if(FactAndActionPredicateString.equals(conditionString))
        {
            String factAndActionValue = "";
            if(factAndActionExp.getAtom() != null)
            {
                factAndActionValue = factAndActionExp.getAtom().get(1).getImage();
                //String conditionValueExp = conditionExp.getChildren().get(1).toString();
                String conditionValueExp = getExpName(conditionExp.getChildren().get(1));

                String conditionValue = "";

                Exp conditionExpFromState = getExpFromFactList(conditionValueExp);
                conditionValue = getValueFromExp(conditionExpFromState);

                Double factAndActionValueDouble = Double.parseDouble(factAndActionValue);
                Double conditionValueDouble = Double.parseDouble(conditionValue);

                if( conditionExp.getConnective().equals( Connective.LESS ))
                {
                    if(factAndActionValueDouble < conditionValueDouble) return true;
                }
                else if( conditionExp.getConnective().equals( Connective.LESS_OR_EQUAL ))
                {
                    if(factAndActionValueDouble <= conditionValueDouble) return true;
                }
                else if( conditionExp.getConnective().equals( Connective.GREATER ))
                {
                    if(factAndActionValueDouble > conditionValueDouble) return true;
                }
                else if( conditionExp.getConnective().equals( Connective.GREATER_OR_EQUAL ))
                {
                    if(factAndActionValueDouble >= conditionValueDouble) return true;
                }
                else if( conditionExp.getConnective().equals( Connective.EQUAL ))
                {
                    if(factAndActionValueDouble.equals(conditionValueDouble)) return true;
                }
            }
            else
            {
                return compareNumericObject( factAndActionExp, conditionExp );
            }
        }
        return false;
    }

    public static boolean compareNumericObject( Exp exp1, Exp exp2 )
    {
//        System.err.println("exp1 "+exp1.toString()+"exp2 "+exp2.toString());
        return exp1.toString().equals( exp2.toString() );
    }

    public static int getOneRandomNumber( int max )
    {
		/*
		 Random rand = new Random();
		int  n = rand.nextInt(50) + 1;		//50 is the maximum and the 1 is our minimum
		 * */
        Random rand = new Random();
        int  random_number = rand.nextInt( max ) + 1;
        return random_number;
    }


}
