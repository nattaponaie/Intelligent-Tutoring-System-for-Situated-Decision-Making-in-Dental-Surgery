package kb.common.controller;

import com.surgical.decision3.common.controller.ApplicationController;

import java.util.ArrayList;
import java.util.List;

import fr.uga.pddl4j.parser.Connective;
import fr.uga.pddl4j.parser.Exp;
import kb.common.datastructure.useraction.UserAction;
import kb.common.datastructure.worldstate.State;
import kb.util.InferenceEngineUtil;
import kb.util.PlanActionUtil;
import kb.util.StudentGraphUtil;

import static kb.util.Utils.getExpName;
import static kb.util.Utils.getValueFromExp;

/**
 * Created by Nattapon on 22/6/2560.
 */

public class PedagogicalController {

    public static List<Exp> undesired_effect_list = new ArrayList<>(); 		//array to keep effect that does not reach the goal.
    public static List<Exp> desired_effect_list = new ArrayList<>(); 		//array to keep effect that does not reach the goal.

    public static boolean isThisActionTheLastAction()
    {
        boolean isTheLastAction = false;

        String lastActionName = ParserController.plan.getAllPlanActions().
                get(ParserController.plan.getAllPlanActions().size()-1).getActionName();

        if(ApplicationController.currentAction.getActionName().equals(lastActionName)) isTheLastAction = true;

        return isTheLastAction;
    }

    public static boolean isSatisfyGoal()
    {
        boolean isSatisfyGoal = false;

        return isSatisfyGoal;
    }

    public static Boolean isActionFollowedSolutionPath(UserAction uAction)
    {
        boolean is_action_followed = false;

        if(uAction.getName().toString().equals(ApplicationController.currentAction.getActionName()))
        {
            is_action_followed = true;
        }

        return is_action_followed;
    }

    public static boolean isEffectSatisfiedDesiredOutCome(UserAction userAction, State currentState )
    {
        boolean is_outcome_expected = false;

        //get desired outcome from plan action
        Exp desired_outcome = PlanActionUtil.getDesiredOutcome(ApplicationController.currentAction);

        // Mes - add IF condition to support a plan that has no desired outcome >> avoid nullException
        if(desired_outcome != null) {
            List<Exp> desired_out_ExpList = null;
            desired_out_ExpList = getGoalExpList(desired_outcome, desired_out_ExpList);

            List<Exp> factAndEffectExpList = currentState.getFactAndEffect();

            for (Exp desired : desired_out_ExpList) {
                System.out.println("--> desired_out_ExpList : " + desired.toString());
            }
            for (Exp eff : factAndEffectExpList) {
                System.out.println("--> factAndEffectExpList : " + eff.toString());
            }

            is_outcome_expected = validateDesiredOutComeAndEffect(factAndEffectExpList, desired_out_ExpList);
        } else {
            // Mes
            is_outcome_expected = true;
            System.out.println("*** The Plan Action: \"" + ApplicationController.currentAction + "\" has no desired outcome");
        }

        System.out.println( "--> is_outcome_expected : " + is_outcome_expected );

        return is_outcome_expected;
    }

    //Process to check if the effect of the action satisfy the goal.
    // (sometimes, the effects of action includes A and NOT_A in the effect list. And A is part of the goal. If we check only A, then it is not correct.
    //Step1: check conflict:  test if each effect item (negated) is available in the goal.
    //Step2: for each item in goal, it must be
    private static boolean validateDesiredOutComeAndEffect( List<Exp> effectExpList, List<Exp> desired_out_ExpList )
    {
        //Initialize list
        System.out.println( "---------------------- validateDesiredOutComeAndEffect ------------------------------" );
        undesired_effect_list = new ArrayList<>();
        desired_effect_list = new ArrayList<>();

        boolean isEffectExistInDesiredOutCome = checkIfEffectExistInDesiredOutCome( effectExpList, desired_out_ExpList );
        //Step1: if goal is found,
//        checkIfEffectIsValidateForGoal( effectExpList, desired_out_ExpList );			//add undesired item to undesired_effect_list
//        checkIfGoalExistInEffectList( effectExpList, desired_out_ExpList );				//add desired item to the desired_effect_list

//        return ( (undesired_effect_list.size() == 0) && ( desired_effect_list.size() > 0) );
        return isEffectExistInDesiredOutCome;
    }

    private static boolean checkIfEffectExistInDesiredOutCome( List<Exp> effectExpList, List<Exp> desired_out_ExpList )
    {
        boolean isEffectExistInDesiredOutCome = false;

        for( Exp desired_outcome : desired_out_ExpList ) {
            if (isFoundFromEffect(effectExpList, desired_outcome)) {
                isEffectExistInDesiredOutCome = true;
            }
            else
            {
                System.out.println(desired_outcome + " not found");
                isEffectExistInDesiredOutCome = false;
                break;
            }
        }

        for( Exp desired_outcome : desired_out_ExpList ) //for update projection node in student graph
        {
            StudentGraphUtil.updateProjectionNode(desired_outcome);
        }

        return isEffectExistInDesiredOutCome;
    }

    private static boolean isFoundFromEffect( List<Exp> effectExpList, Exp desired_outcome )
    {
        boolean isFound = false;
        if(desired_outcome.isAllow_explore())
        {
            isFound = true;
        }
        else
        {
            for( Exp effectExp : effectExpList )
            {

                String effectName = getExpName(effectExp);
                String desired_outcomeName = getExpName(desired_outcome);
                if (desired_outcome.getChildren().size() != 0)
                {
                    //If this fact has NOT Connective
                    if(desired_outcome.getConnective().equals(Connective.NOT))
                    {
                        //if this condition has value
                        if(desired_outcome.getChildren().get(0).getAtom().size() == 2)
                        {
                            String desiredSecondAtom = InferenceEngineUtil.getSecondAtomStringFromPredicate(desired_outcome);
                            String effectSecondAtom = InferenceEngineUtil.getSecondAtomStringFromPredicate(effectExp);

                            if(!effectSecondAtom.equals(desiredSecondAtom))
                            {
                                System.err.println("desired_outcome " + desired_outcome +" is found in effectExpList");
                                isFound = true;
                                break;
                            }
                        }
                        else if(desired_outcome.getChildren().get(0).getAtom().size() == 1)
                        {
                            if(effectName.equals(desired_outcomeName) && effectExp.getConnective().equals(desired_outcome.getConnective()))
                            {
                                System.err.println("desired_outcome " + desired_outcome +" is found in effectExpList");
                                isFound = true;
                                break;
                            }
                        }
                    }
                    else if(desired_outcome.getChildren().size() == 1)
                    {
                        if(effectName.equals(desired_outcomeName) && effectExp.getConnective().equals(desired_outcome.getConnective()))
                        {
                            System.err.println("desired_outcome " + desired_outcome +" is found in effectExpList");
                            isFound = true;
                            break;
                        }
                    }
                    else if(desired_outcome.getChildren().size() == 2)//this desired outcome has variable to compare
                    {
                        if( desired_outcome.getConnective().equals( Connective.LESS ) ||
                                desired_outcome.getConnective().equals( Connective.LESS_OR_EQUAL) ||
                                desired_outcome.getConnective().equals( Connective.GREATER) ||
                                desired_outcome.getConnective().equals( Connective.GREATER_OR_EQUAL ) ||
                                desired_outcome.getConnective().equals( Connective.EQUAL))
                        {
                            Double effectValue = Double.parseDouble(getValueFromExp(effectExp));
                            Double desired_outcomeValue = Double.parseDouble(getValueFromExp(desired_outcome));
                            if(effectName.equals(desired_outcomeName))
                            {
                                if( desired_outcome.getConnective().equals( Connective.LESS ))
                                {
                                    System.err.println("desired_outcome " + desired_outcome +" is found in effectExpList");
                                    if(desired_outcomeValue < effectValue) return true;
                                }
                                else if( desired_outcome.getConnective().equals( Connective.LESS_OR_EQUAL ))
                                {
                                    System.err.println("desired_outcome " + desired_outcome +" is found in effectExpList");
                                    if(desired_outcomeValue <= effectValue) return true;
                                }
                                else if( desired_outcome.getConnective().equals( Connective.GREATER ))
                                {
                                    System.err.println("desired_outcome " + desired_outcome +" is found in effectExpList");
                                    if(desired_outcomeValue > effectValue) return true;
                                }
                                else if( desired_outcome.getConnective().equals( Connective.GREATER_OR_EQUAL ))
                                {
                                    System.err.println("desired_outcome " + desired_outcome +" is found in effectExpList");
                                    if(desired_outcomeValue >= effectValue) return true;
                                }
                                else if( desired_outcome.getConnective().equals( Connective.EQUAL ))
                                {
                                    System.err.println("desired_outcome " + desired_outcome +" is found in effectExpList");
                                    if(desired_outcomeValue.equals(effectValue)) return true;
                                }
                            }
                        }
                        else
                        {
                            String effectValue = getValueFromExp(effectExp);
                            String desired_outcomeValue = getValueFromExp(desired_outcome);
                            if(effectName.equals(desired_outcomeName) && effectValue.equals(desired_outcomeValue))
                            {
                                System.err.println("desired_outcome " + desired_outcome +" is found in effectExpList");
                                isFound = true;
                                break;
                            }
                        }
                    }
                }
                else
                {
                    if(desired_outcome.getAtom() != null)
                    {
                        if(desired_outcome.getAtom().size() == 2)
                        {
                            String desired_outcome_string = InferenceEngineUtil.getAtomStringFromPredicate(desired_outcome);
                            String effect_string = InferenceEngineUtil.getAtomStringFromPredicate(effectExp);

                            if (desired_outcome_string.equals(effect_string)) {
                                String desiredSecondAtom = InferenceEngineUtil.getSecondAtomStringFromPredicate(desired_outcome);
                                String effectSecondAtom = InferenceEngineUtil.getSecondAtomStringFromPredicate(effectExp);

                                if (desiredSecondAtom.equals(effectSecondAtom))
                                {
                                    System.err.println("desired_outcome " + desired_outcome +" is found in effectExpList");
                                    isFound = true;
                                    break;
                                }
                            }
                        }
                        else if(effectName.equals(desired_outcomeName) && effectExp.getConnective().equals(desired_outcome.getConnective()))
                        {
                            System.err.println("desired_outcome " + desired_outcome +" is found in effectExpList");
                            isFound = true;
                            break;
                        }
                    }
                    else if(effectName.equals(desired_outcomeName) && effectExp.getConnective().equals(desired_outcome.getConnective()))
                    {
                        System.err.println("desired_outcome " + desired_outcome +" is found in effectExpList");
                        isFound = true;
                        break;
                    }
                }
//
//
//                if( effectExp.equals( desired_outcome ) && effectExp.getConnective().equals(desired_outcome.getConnective()))
//                {
//                    System.err.println("desired_outcome " + desired_outcome +" is found in effectExpList");
//                    isFound = true;
//                    break;
//                }
            }
        }

        return isFound;
    }

    public static List<Exp> getGoalExpList(Exp goal, List<Exp> goalList )
    {
        if( goalList == null )
        {
            goalList = new ArrayList<>();
        }

        if( goal.getConnective().equals( Connective.NOT )
                || goal.getConnective().equals( Connective.ATOM )
//			|| goal.getConnective().equals( Connective.LESS )
//			|| goal.getConnective().equals( Connective.LESS_OR_EQUAL)
//			|| goal.getConnective().equals( Connective.GREATER)
//			|| goal.getConnective().equals( Connective.GREATER_OR_EQUAL )
//			|| goal.getConnective().equals( Connective.EQUAL)
                )
        {
            goalList.add(goal);
        }
        else if( goal.getConnective().equals( Connective.AND ) )		//there are more children
        {
            for(Exp g: goal.getChildren())
            {
                goalList = getGoalExpList( g, goalList);
            }
        }

        return goalList;
    }

    public static List<Exp> getEffectExpList( Exp effect, List<Exp> effectExpList )
    {
        if( effectExpList == null )
        {
            effectExpList = new ArrayList<>();
        }

        if( effect.getConnective().equals( Connective.NOT )
                || effect.getConnective().equals( Connective.ATOM )
			    || effect.getConnective().equals( Connective.LESS )
			    || effect.getConnective().equals( Connective.LESS_OR_EQUAL)
			    || effect.getConnective().equals( Connective.GREATER)
			    || effect.getConnective().equals( Connective.GREATER_OR_EQUAL )
                || effect.getConnective().equals( Connective.EQUAL)
                || effect.getConnective().equals( Connective.FN_ATOM)
                )
        {
            effectExpList.add( effect );
        }
        //else if( effect.getChildren().size() > 0 )
        else if( effect.getConnective().equals( Connective.AND ) && ( effect.getChildren().size() > 0 ) )
        {
            for(Exp eff: effect.getChildren())
            {
                //effectList.add( eff.getAtom().get(0));
                effectExpList = getEffectExpList( eff, effectExpList);
            }
        }

        // Mes - to handle ONEOF effect
        else if( effect.getConnective().equals(Connective.ONEOF)){
            for(Exp eff: effect.getChildren())
            {
                //effectList.add( eff.getAtom().get(0));
                effectExpList = getEffectExpList( eff, effectExpList);
            }
        }


        return effectExpList;
    }
}
