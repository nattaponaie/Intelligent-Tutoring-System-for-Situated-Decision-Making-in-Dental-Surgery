package com.surgical.decision3.functions;

import com.surgical.decision3.common.bean.datastream.DataStream;
import com.surgical.decision3.common.controller.ApplicationController;
import com.surgical.decision3.panel.MainActivity;

import java.util.ArrayList;
import java.util.List;

import fr.uga.pddl4j.parser.Domain;
import fr.uga.pddl4j.parser.Exp;
import kb.common.controller.ParserController;
import kb.common.controller.PedagogicalController;
import kb.common.datastructure.graph.StudentGraph;
import kb.common.datastructure.solutionpath.Plan;
import kb.common.datastructure.useraction.UserAction;
import kb.common.tutorpointer.TutorPointer;
import kb.util.InferenceEngineUtil;
import kb.util.PlanActionUtil;
import kb.util.PlanProjectionUtil;
import kb.util.StudentGraphUtil;
import kb.util.UserActionUtil;
import kb.util.WorldStateUtil;

import static com.surgical.decision3.common.controller.ApplicationController.currentAction;
import static com.surgical.decision3.common.controller.ApplicationController.studentGraphList;
import static kb.util.PlanProjectionUtil.combineProjectionEffectToCurrentFactList;
import static kb.util.WorldStateUtil.getCurrentState;

/**
 * Created by Nattapon on 6/7/2560.
 */

public class TransformStudentAction {

    private static Domain domain = ParserController.domain;
    private static Plan plan = ParserController.plan;
    public static StudentGraph studentGraphObject = new StudentGraph();
    public static boolean is_allowed_to_continue = false;
    public static String actionInputName = "";
    public static List<Exp> studentProjectionEffect = new ArrayList();
    public static boolean is_action_followed_plan = false;

    public static void ReceiveStudentAction()
    {
        //Firstly transform student action into ground object
        DataStream dt = MainActivity.getDataStream();
        boolean isStudentInputEmpty = InferenceEngineUtil.transformStudentActionToGround(dt);
        boolean isInvalidAction = false;

        if(isStudentInputEmpty == false && plan != null && domain != null) {

//            //set to default = false
//            ApplicationController.ready_for_next_state = false;

            //Get action predicate from selected step
            UserActionUtil.getUserActionPredicate();

            //transform student action into predicate format
            UserAction uAction = UserActionUtil.transformIntoQuantitativeAndQualitative(domain);

            WorldStateUtil.generateNewState(WorldStateUtil.getCurrentFactAndEffect());

            //add state,perception,comprehension node to student graph
            StudentGraphUtil.addStudentStateGraph();

            //check the action is it followed the solution path or not
            boolean is_action_followed_path = PedagogicalController.isActionFollowedSolutionPath(uAction);
            actionInputName = uAction.getName().toString();

            //NUI: Update applicationController
            if(is_allowed_to_continue)
            {
                ApplicationController.isActionCorrect = true;
                is_action_followed_path = true;
            }
            else {
                ApplicationController.isActionCorrect = is_action_followed_path;
            }
            System.out.println( "====================================" );
            System.out.println( "--> isActionCorrect: " + is_action_followed_path);
            System.out.println( "====================================" );

//            addActionManual("TOOL_SET","X_SET",false);

            //set to default = false
            //ApplicationController.ready_for_next_state = false;
            TransformStudentAction.is_action_followed_plan = is_action_followed_path;

            if (is_action_followed_path || is_allowed_to_continue){

                //Grab effects from domain, this is effect that is not validated.
                Exp action_effect = PlanProjectionUtil.processActionToGetEffect(uAction);

                if(action_effect != null)
                {
                    combineProjectionEffectToCurrentFactList(action_effect);

                    WorldStateUtil.printAllState();

                    boolean is_outcome_expected = PedagogicalController.isEffectSatisfiedDesiredOutCome(uAction, getCurrentState());

                    //Nui Add is_outcome_expected to applicationController
                    ApplicationController.isEffectSatisfyDesiredOutcomes = is_outcome_expected;
                    System.out.println( "====================================" );
                    System.out.println( "--> isEffectSatisfyDesiredOutcomes: " + is_outcome_expected);
                    System.out.println( "====================================" );

                    if(is_outcome_expected)
                    {

                        //NOTE: Moving to the next state should be done after generating the explanation.
                        //set the next step
                        //THIS CODE IS MOVE TO THE StrategyController.processForTheNextAction

                        //----------------------
                        //OLD CODE - After merge
                        //----------------------
                        System.err.println(">> Ready for receiving next action ! <<");
                        System.out.println( "====================================" );

                        WorldStateUtil.printCurrentTrailHistory();

                        //set ready for next state to 'true'
                        ApplicationController.ready_for_next_state = true;
                    }
                    else
                    {
                        ///* OLD CODE
                        //set ready for next state to 'false'
                        ApplicationController.ready_for_next_state = false;
                        //clone and create the trail history
                        WorldStateUtil.cloneAndCreateTrailHistory();

                        System.err.println(">> Please go back to make it right ! <<");
                        System.out.println( "====================================" );

                        WorldStateUtil.printCurrentTrailHistory();
                    }

                    StudentGraphUtil.updateStudentStateNode(studentGraphObject);
                    StudentGraphUtil.addPerceptionAndComprehensionNode();
                    StudentGraphUtil.processStudentNode(is_outcome_expected, isInvalidAction);

                }
                System.out.println(studentGraphList);
//                StudentGraphUtil.printAllStudentGraph();
//                System.out.println("student Graph size: "+ApplicationController.studentGraphList.size());
//                List<GraphNode> graph = StudentGraphUtil.getCurrentStudentGraph();
//                if(graph != null) StudentGraphUtil.printStudentGraphDetails(graph);

            }
            else
            {
                //********** move pointer to the next sub plan (rb_plan) here !!**********

                //what do we have to check?
                //1. check that what is a sub plan that user input
                //2. if the action that user input is not in the next sub plan of la_plan so it shouldn't move
                /***
                 * initially, la_plan is the first sub plan
                 * then user input (insert_rubber_dam) so the next sub plan should be the next sub plan of la_plan (rb_plan)
                 * and point currentAction to first action of the next sub plan of la_plan (rb_plan)
                 * NOTE: if
                 */

                Plan currentSubPlan = ApplicationController.currentAction.getParentPlan();
                if(currentSubPlan != null)
                {
                    if(currentSubPlan.getOptionalCondition() != null) {

                        // Mes: if the tutor is pointing at the first action of the optional plan
                        // and if user inputs the first action of the next plan,
                        // then it will be allowed to skip to the next subplan.
                        if(is_currentAction_firstAction_in_currentSubplan() && is_uaction_firstAction_in_next_subplan(uAction)) {
                            is_allowed_to_continue = WorldStateUtil.isPredicateFoundInFact(currentSubPlan.getOptionalCondition());
                        }
                    }
                }
                if(is_allowed_to_continue)
                {
                    TutorPointer.pointToFirstPlanAction_inNextPlan();
                    ReceiveStudentAction();
                } else {
                    System.err.println("Student's action is not related to solution path! (hint: "
                            + currentAction.getActionName() + ")");

                    //                StudentGraphUtil.addEndNodeInvalidAction(true);
                    ApplicationController.ready_for_next_state = false;

                    StudentGraphUtil.addActionNode(PlanActionUtil.getPlanAction(uAction.getName().toString()));
                    isInvalidAction = true;
                    StudentGraphUtil.processStudentNode(false, isInvalidAction);

                    //                StudentGraphUtil.printAllStudentGraph();
                }
            }
        }
        else
        {
            System.err.println("Student's input is empty !");
        }
    }

    private static boolean is_currentAction_firstAction_in_currentSubplan() {
        String currentActionName = ApplicationController.currentAction.getActionName();

        Plan currentSubplan = ApplicationController.currentAction.getParentPlan();
        String firstActionInCurrentSubplan = currentSubplan.getPlanActionList().get(0).getActionName();
        if(currentActionName.equals(firstActionInCurrentSubplan)){
            return true;
        }
        return false;
    }

    private static boolean is_uaction_firstAction_in_next_subplan(UserAction uAction) {

        String actionNameString = uAction.getName().getImage();

        Plan nextSubplan_currentAction = TutorPointer.getNextPlan();
        String firstAction_nextSubplan = nextSubplan_currentAction.getPlanActionList().get(0).getActionName();
        if(actionNameString.equals(firstAction_nextSubplan)){
            return true;
        }
        return false;
    }



}
