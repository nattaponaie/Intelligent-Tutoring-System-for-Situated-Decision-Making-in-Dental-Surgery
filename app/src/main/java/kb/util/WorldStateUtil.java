package kb.util;

import com.surgical.decision3.common.controller.ApplicationController;

import java.util.ArrayList;
import java.util.List;

import fr.uga.pddl4j.parser.Exp;
import kb.common.datastructure.worldstate.State;

import static com.surgical.decision3.common.controller.ApplicationController.ready_for_next_state;
import static com.surgical.decision3.common.controller.ApplicationController.worldStateList;

/**
 * Created by Nattapon on 2/7/2560.
 */

public class WorldStateUtil {

    public static boolean isPredicateFoundInFact(Exp predicate)
    {
        for(Exp each_predicate : WorldStateUtil.getCurrentState().getFactPredicate())
        {
            if(each_predicate.equals(predicate))
            {
                return true;
            }
        }
        return false;
    }

    public static void updateState(List<Exp> actionFactList)
    {
        WorldStateUtil.getCurrentState().getFactAndActionPredicate().clear();//clear Action predicate in WorldState
        WorldStateUtil.getCurrentState().getFactAndActionPredicate().addAll(actionFactList);
    }

    public static void generateNewState(List<Exp> currentFacts)
    {
        if(ready_for_next_state) {
            State s = new State(WorldStateUtil.getCurrentStateID() + 1);
            s.setFactPredicate(currentFacts);
//            s.setFactAndActionPredicate(WorldStateUtil.getCurrentFactAndEffect());

            ApplicationController.worldStateList.add(s);

        }
    }

//    public static void generateNewState(List<Exp> currentFacts, List<Exp> actionFactList)
//    {
//        if(ready_for_next_state) {
//            State s = new State(WorldStateUtil.getCurrentStateID() + 1);
//            s.setFactPredicate(currentFacts);
//            s.setFactAndActionPredicate(actionFactList);
//
//            ApplicationController.worldStateList.add(s);
//
//        }
//        else
//        {
//            WorldStateUtil.getCurrentState().setFactAndActionPredicate(actionFactList);
//        }
//    }

    public static State cloneCurrentState(State currentState)
    {
        State clone_state = new State(currentState.getS_id());

        clone_state.setFactPredicate(currentState.getFactPredicate());
        clone_state.setFactAndActionPredicate(currentState.getFactAndActionPredicate());
        clone_state.setFactAndEffect(currentState.getFactAndEffect());
        clone_state.setTrialHistory(currentState.getTrialHistory());

        return clone_state;
    }

    public static void printCurrentTrailHistory()
    {
        if(WorldStateUtil.getCurrentState().getTrialHistory() != null)
        {
            for(State trail_state : WorldStateUtil.getCurrentState().getTrialHistory())
            {
                System.err.println("trail_history");
                System.out.println("State ID "+trail_state.getS_id());
                System.out.println("Predicate "+trail_state.getFactPredicate());
                System.out.println("FactAndActionPredicate "+trail_state.getFactAndActionPredicate());
                System.out.println("FactAndEffect "+ trail_state.getFactAndEffect());
            }
        }
    }

    public static void cloneAndCreateTrailHistory()
    {
        ArrayList<State> failed_state = new ArrayList<>();
        //clone state
        State clone_state = new State(WorldStateUtil.getCurrentStateID());
        clone_state.setFactPredicate(WorldStateUtil.getCurrentFactPredicate());
        clone_state.setFactAndActionPredicate(WorldStateUtil.getCurrentFactAndActionPredicate());
        clone_state.setFactAndEffect(WorldStateUtil.getCurrentFactAndEffect());

        failed_state.add(clone_state);

        if(getCurrentState().getTrialHistory() == null) getCurrentState().setTrialHistory(failed_state);
        else getCurrentState().getTrialHistory().add(clone_state);
    }

    public static void printAllState()
    {
        System.out.println("All State");
        for (State allWorldState : WorldStateUtil.getWorldStatesList()) {
            System.err.println("State ID "+ allWorldState.getS_id());
            System.err.println("Fact");
            System.out.println(allWorldState.getFactPredicate());
            System.err.println("FactAndAction");
            System.out.println(allWorldState.getFactAndActionPredicate());
            System.err.println("FactAndEffect");
            System.out.println(allWorldState.getFactAndEffect());
            System.err.println("----------------------");
        }
    }

    public static List<Exp> getCurrentFactPredicate()//get current Predicate from current world state
    {
        return worldStateList.get( ( worldStateList.size() - 1 ) ).getFactPredicate();
    }

    public static List<Exp> getCurrentFactAndActionPredicate()//get current FactAndActionPredicate from current world state
    {
        return worldStateList.get( ( worldStateList.size() - 1 ) ).getFactAndActionPredicate();
    }

    public static List<Exp> getCurrentFactAndEffect()//get current Fact and Effect from current world state
    {
        return worldStateList.get( ( worldStateList.size() - 1 ) ).getFactAndEffect();
    }

    public static ArrayList<State> getCurrentTrailHistory()
    {
        return worldStateList.get( ( worldStateList.size() - 1 ) ).getTrialHistory();
    }

    public static Integer getCurrentStateID()//get state id from current world state
    {
        return worldStateList.get( worldStateList.size() -1  ).getS_id();
    }

    public static State getCurrentState()//get current state from world state list
    {
        return worldStateList.get( ( worldStateList.size() - 1 ) );
    }

    public static void setCurrentFactPredicate(List<Exp> list)//set Predicate list to current world state
    {
        worldStateList.get( ( worldStateList.size() - 1 ) ).setFactPredicate(list);
    }

    public static void setCurrentFactAndActionPredicate(List<Exp> list)//set FactAndActionPredicate list to current world state
    {
        worldStateList.get( ( worldStateList.size() - 1 ) ).setFactAndActionPredicate(list);
    }

    public static void setCurrentFactAndEffect(List<Exp> list)//set fact and effect list to current world state
    {
        worldStateList.get( ( worldStateList.size() - 1 ) ).setFactAndEffect(list);
    }

    public static void setWorldStateList(ArrayList<State> worldList)
    {
        worldStateList = worldList;
    }

    public static List<State> getWorldStatesList()//get all states/facts
    {
        return worldStateList;
    }

}
