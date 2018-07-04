package kb.common.datastructure.worldstate;

import java.util.ArrayList;
import java.util.List;

import fr.uga.pddl4j.parser.Exp;

/**
 * Created by Nattapon on 7/6/2560.
 */

public class State {

    List<Exp> factPredicate;
    List<Exp> factAndActionPredicate;
    List<Exp> factAndEffect;
    Integer S_id = 0;

    ArrayList<State> trialHistory = null;

    public ArrayList<State> getTrialHistory() {
        return trialHistory;
    }

    public void setTrialHistory(ArrayList<State> trialHistory) {
        this.trialHistory = new ArrayList<>();
        this.trialHistory = trialHistory;
    }

    public State( int S_id )
    {
        super();

        this.S_id = S_id;

        if( this.factPredicate == null )
        {
            this.factPredicate = new ArrayList<>();
        }
        if( this.factAndActionPredicate == null )
        {
            this.factAndActionPredicate = new ArrayList<>();
        }
        if( this.factAndEffect == null )
        {
            this.factAndEffect = new ArrayList<>();
        }
    }

    public Integer getS_id() {return S_id;}
    public void setS_id(Integer S_id)
    {
        this.S_id = S_id;
    }

    public List<Exp> getFactPredicate() {
        return factPredicate;
    }

    public void setFactPredicate(List<Exp> predicate) {
        this.factPredicate = predicate;
    }

    public List<Exp> getFactAndActionPredicate() {
        return factAndActionPredicate;
    }

    public void setFactAndActionPredicate(List<Exp> factAndActionPredicate) {
        this.factAndActionPredicate = factAndActionPredicate;
    }

    public List<Exp> getFactAndEffect() {
        return factAndEffect;
    }

    public void setFactAndEffect(List<Exp> factAndEffect) {
        this.factAndEffect = factAndEffect;
    }
}
