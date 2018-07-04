package kb.util;

import com.surgical.decision3.common.controller.ApplicationController;

import fr.uga.pddl4j.parser.Exp;
import kb.common.datastructure.solutionpath.Plan;
import kb.common.datastructure.solutionpath.PlanAction;
import kb.common.controller.ParserController;

/**
 * Created by Nattapon on 5/7/2560.
 */

public class PlanActionUtil {

    public static PlanAction getPlanAction(String actionName) {

        PlanAction planAction = null;

        for (PlanAction eachPlanAction : ParserController.plan.getAllPlanActions()) {
            if (actionName.equals(eachPlanAction.getActionName())) {
                planAction = eachPlanAction;
            }
        }

        return planAction;
    }

    public static Exp getDesiredOutcome(PlanAction planAction)
    {
        Exp desired_outcome = null;
        for(PlanAction allPlanAction : ParserController.plan.getAllPlanActions())
        {
            if(planAction.equals(allPlanAction)) {
                desired_outcome = allPlanAction.getDesired_outcomes();
            }
        }

        return desired_outcome;
    }

    // Comment from Mes: This method can be done by using "planAction.getParentPlan()"
    public static Plan getCurrentSubPlan()
    {
        for(Plan each_sub_plan : ParserController.plan.getSubplan())
        {
            for(PlanAction each_plan_action : each_sub_plan.getPlanActionList())
            {
                if(each_plan_action.getActionName().equals(ApplicationController.currentAction.getActionName()))
                {
                    return each_sub_plan;
                }
            }
        }
        return null;
    }

}
