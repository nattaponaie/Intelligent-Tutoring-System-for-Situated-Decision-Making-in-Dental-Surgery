package kb.util;

import com.surgical.decision3.common.controller.ApplicationController;

import java.util.ArrayList;
import java.util.List;

import fr.uga.pddl4j.parser.Exp;
import fr.uga.pddl4j.parser.Op;
import fr.uga.pddl4j.parser.Symbol;

/**
 * Created by Nattapon on 7/6/2560.
 */

public class OpUtil {

    public static List<Op> groundActionList = new ArrayList<>();

    public static Op getGroundAction(Symbol actionName)
    {
        Op groundAction = null;
        for(Op actList : groundActionList)
        {
            if(actList.getName().equals(actionName))
            {
                groundAction = actList;
            }
        }
        return groundAction;
    }

    public static Exp getGroundAction(String actionName)
    {
        Op groundAction = null;
        for(Op actList : groundActionList) {
            if (actList.getName().toString().equals(actionName)) {
                groundAction = actList;
            }
        }
        Exp effectSource = null;

        return effectSource = groundAction != null ? groundAction.getEffects() : null;
    }

    public static Exp getCurrentEffectActionCondition()
    {
        Op groundAction = null;
        for(Op actList : groundActionList) {
            if (actList.getName().toString().equals(ApplicationController.currentAction.getActionName())) {
                groundAction = actList;
            }
        }

        Exp effectSource = groundAction != null ? groundAction.getEffects() : null;

        return effectSource;
    }

    public static List<Exp> getActionConditionFromGround(String actionName)
    {
        List<Exp> actionConditionList = new ArrayList<>();

        Op groundAction = null;
        for(Op actList : groundActionList) {
            if (actList.getName().toString().equals(actionName)) {
                groundAction = actList;
            }
        }

        Exp effectSource = groundAction != null ? groundAction.getEffects() : null;

        if((effectSource != null ? effectSource.getChildren() : null) != null && effectSource.getChildren().size() > 0 ) {
            for (Exp ex : effectSource.getChildren()) {
                if (ex.getConnective().getImage().equals("when")) {
                    actionConditionList.add(ex.getChildren().get(0));
                }
            }
        }

        return actionConditionList;
    }

    public static List<Exp> getProjectionEffectFromGround(String actionName)
    {
        List<Exp> projectionEffectList = new ArrayList<>();

        Op groundAction = null;
        for(Op actList : groundActionList) {
            if (actList.getName().toString().equals(actionName)) {
                groundAction = actList;
            }
        }

        Exp effectSource = groundAction != null ? groundAction.getEffects() : null;

        if((effectSource != null ? effectSource.getChildren() : null) != null && effectSource.getChildren().size() > 0 ) {
            for (Exp ex : effectSource.getChildren()) {
                if (ex.getConnective().getImage().equals("when")) {
                    Exp effectExp = ex.getChildren().get(1);
                    projectionEffectList.add(effectExp);
                }
            }
        }

        return projectionEffectList;
    }
}
