package kb.common.tutorpointer;

import com.surgical.decision3.common.controller.ApplicationController;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import fr.uga.pddl4j.parser.Connective;
import fr.uga.pddl4j.parser.Exp;
import kb.common.datastructure.solutionpath.Plan;
import kb.common.datastructure.solutionpath.PlanAction;
import kb.common.controller.ParserController;
import kb.util.Utils;
import kb.util.WorldStateUtil;

/**
 * Created by mess on 7/6/2017.
 */

public class TutorPointer {

    public static boolean isFirstActionOfPlan( PlanAction planAction )
    {
        boolean isFirstAction = false;

        if( planAction.getParent() == null )
        {
            isFirstAction = true;
        }

        return isFirstAction;
    }

    // to support conditional action in the first action
    public static PlanAction pointToFirstPlanAction(){
        ApplicationController.currentAction = ParserController.plan.getAllPlanActions().get(0);
        PlanAction firstAction = getPlanActionAfterSimpleAction(ApplicationController.currentAction);
        return firstAction;
    }

    // Mes warning - does not handle final action yet. >> will cause error after final action
    public static void pointToNextPlanAction() {

        System.out.println("\n----------------------- TutorPointer pointToNextPlanAction -----------------------\n");

        // if not satisfied desired_outcomes, then use the same pointer
        if (ApplicationController.ready_for_next_state) {
            System.out.println("Tutor message: Nice! you are doing it right. Moving pointer to the next plan action.");
            PlanAction nextAction = getNextPlanAction();
            ApplicationController.currentAction = nextAction;  //move pointer to the next action
        } else {
            System.out.println("Tutor message: Unable to move pointer from \"" + ApplicationController.currentAction + "\"");
            System.out.println("Tutor message: Please make sure that the action and resulted outcomes match the declaration in the PDL");
        }
        System.err.println("Tutor status: waiting for action: \"" + ApplicationController.currentAction + "\"");
        System.out.println("\n-------------------------- TutorPointer Ended --------------------------");
    }

    public static PlanAction getNextPlanAction() {
        PlanAction currentAction = ApplicationController.currentAction;
        PlanAction nextAction = ApplicationController.currentAction.getChild();
        PlanAction confirmedNextAction = null;

        // when current action has a child
        if(nextAction!=null){
            confirmedNextAction = getPlanActionAfterSimpleAction(nextAction);

        } else {       // when no child action is detected >> last action or last subaction
            confirmedNextAction = getPlanActionAfterLast(currentAction);
        }
        return confirmedNextAction;
    }

    private static PlanAction getPlanActionAfterSimpleAction(PlanAction nextAction) {

        // when next action is conditional action if/while
        PlanAction confirmedNextAction = null;

        if(nextAction.isConditionalAction()){
            confirmedNextAction = getBranchedAction(nextAction);       // determine to get IF or ELSE or SKIP the conditional action

        } else {    // when next action is a simple action
            confirmedNextAction = nextAction;
        }
        return confirmedNextAction;
    }

    private static PlanAction getPlanActionAfterLast(PlanAction lastAction){
        PlanAction parentSubAction = lastAction.getParentSubAction();
        PlanAction confirmedNextAction = null;

        PlanAction.ConditionType parentSubActionConditionType = parentSubAction.getActionConditionType();

        switch (parentSubActionConditionType){
            case LOOP:
                confirmedNextAction = getBranchedAction(parentSubAction);
                break;

            case BRANCH:
                confirmedNextAction = parentSubAction.getChild();
                break;

            default:
                confirmedNextAction = null;
        }
        return confirmedNextAction;
    }

    // to retrieve next action node that is conditional action
    private static PlanAction getBranchedAction(PlanAction conditionalAction) {
        Exp actionCondition = conditionalAction.getActionCondition();

        boolean isWhenConditionMetFact = isWhenConditionMetFact(actionCondition);    //working only after 1 action

        List<PlanAction> subActionList = conditionalAction.getSubActionList();
        List<PlanAction> subActionElseList = conditionalAction.getSubActionElseList();

        PlanAction confirmedNextAction = null;

        // when condition are satisfied
        if(isWhenConditionMetFact){
            System.err.println("Tutor message: The current worldstate satisfied condition of the upcoming conditional action: " + conditionalAction);
            confirmedNextAction = subActionList.get(0);

        } else {    // when conditions are not satisfied
            System.err.println("Tutor message: The current worldstate does not satisfied the condition of the upcoming conditional action: " + conditionalAction);
            System.err.println("\"" + conditionalAction + "\" has been skipped.");

            //when there is else in the action (only in IF)
            if (subActionElseList != null && subActionElseList.size() > 0) {
                confirmedNextAction = subActionElseList.get(0);
            } else {            // work for both IF and WHILE
                confirmedNextAction = conditionalAction.getChild();
            }
        }

        return confirmedNextAction;
    }


    // Mes - Copy from WorldStateUtil - to compare actionCondition and the current world state.
    private static boolean isWhenConditionMetFact(Exp conditionExp)
    {
        //System.out.println( "--- isWhenConditionMetFact ---" );
        boolean isWhenConditionMet = false;

        //List<Exp> currentFactList = getCurrentFacts();
        List<Exp> currentActAndPredFactList = WorldStateUtil.getCurrentFactAndEffect();     // Mes - added to get all fact of the current state.
        //List<Exp> currentActAndPredFactList = WorldStateUtil.getCurrentFactAndActionPredicate();


        boolean isExist = false;

        if( currentActAndPredFactList == null || currentActAndPredFactList .size() == 0 )
        {
            return false;
        }

        //----------------------------------------------
        // Step1: extract to "considerList"
        //----------------------------------------------
        //Fix bug. If conditionExp has connective AND, this is ok.
        //If conditionExp is NOT, it takes the children to compare, which is P, instead of -P
        //----------------------------------------------
        List<Exp> considerList = new ArrayList<>();

        if( conditionExp.getConnective().equals( Connective.LESS ) ||
                conditionExp.getConnective().equals( Connective.LESS_OR_EQUAL) ||
                conditionExp.getConnective().equals( Connective.GREATER) ||
                conditionExp.getConnective().equals( Connective.GREATER_OR_EQUAL ) ||
                conditionExp.getConnective().equals( Connective.EQUAL)
                )
        {
            considerList.add(conditionExp);
        }
        else if( conditionExp.getConnective().equals( Connective.NOT ) )
        {
            //if( the connection of the first child is numeric, it is like above. )
            // for the case (not(<(drill_size)(pc_widt)))
//			Exp firstChild = conditionExp.getChildren().get(0);
//			if( firstChild != null && isNumericConnective( firstChild ) )
//			{
//				considerList.add(conditionExp);
//			}

            considerList.add(conditionExp);
        }
        else if( conditionExp.getChildren().size() == 0 )
        {
            //if there is no children.
            considerList.add(conditionExp);
        }
        else
        {
            considerList = conditionExp.getChildren();
        }

        //for( Exp cExpChild : conditionExp.getChildren() )
        for( Exp cExpChild : considerList )
        {
            boolean isAvailable = false;

            if( cExpChild.getConnective().equals( Connective.LESS ) ||
                    cExpChild.getConnective().equals( Connective.LESS_OR_EQUAL) ||
                    cExpChild.getConnective().equals( Connective.GREATER) ||
                    cExpChild.getConnective().equals( Connective.GREATER_OR_EQUAL ) ||
                    cExpChild.getConnective().equals( Connective.EQUAL)
                    )
            {
                //Special compare.
                //TODO clarify functionWithValueHashMap in tutor
                HashMap<Exp, Double> functionWithValueHashMap = new HashMap<>();
                isAvailable = Utils.isAvailable_NumericComparison(currentActAndPredFactList, cExpChild, functionWithValueHashMap);
            }
            else
            {
                isAvailable = Utils.isAvailable(  currentActAndPredFactList , cExpChild );
            }

            if( isAvailable )
            {
                //Note: check if connective must be check or not.
                isExist = true;
            }
            else
            {
                isExist = false;
                return false; //every item must meet condition
            }
        }

        if( isExist )
        {
            isWhenConditionMet = true;
        }

        //System.out.println( "--> Meet the condition: " + isWhenConditionMet );

        return isWhenConditionMet;
    }

    public static void pointToFirstPlanAction_inNextPlan(){
        ApplicationController.currentAction = TutorPointer.getNextPlan().getPlanActionList().get(0);
    }

    // Mes: This is Temporary Solution - parent and child should be added in each plan.
    public static Plan getNextPlan(){
        PlanAction tempAction = ApplicationController.currentAction;

        Plan resultPlan = null;
        Plan planIndex = tempAction.getParentPlan();

        while(planIndex == tempAction.getParentPlan()){
            tempAction = tempAction.getChild();
            if(tempAction == null){
                return null;
            }
        }
        resultPlan = tempAction.getParentPlan();

        return resultPlan;
    }

}
