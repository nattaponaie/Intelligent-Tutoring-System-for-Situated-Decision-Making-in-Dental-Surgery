package kb.common.datastructure.useraction;

import java.util.ArrayList;
import java.util.List;

import fr.uga.pddl4j.parser.Exp;
import fr.uga.pddl4j.parser.Symbol;
import fr.uga.pddl4j.parser.TypedSymbolValue;

/**
 * Created by Nattapon on 8/6/2560.
 */

public class UserAction
{
    Symbol name;

    List<TypedSymbolValue> parameters;

    public ArrayList<UserActionParameter> userActionWithValue = new ArrayList<>();

    List<Exp> user_action_predicate_list;

    public UserAction()
    {
        super();
        this.parameters = new ArrayList<>();
        user_action_predicate_list = new ArrayList<>();
    }

    public Symbol getName()
    {
        return name;
    }

    public void setName(Symbol name)
    {
        this.name = name;
    }

    public List<TypedSymbolValue> getParameters() {
        return parameters;
    }

    public void setParameters(List<TypedSymbolValue> parameters) {
        this.parameters = parameters;
    }

    public List<Exp> getUser_action_predicate_list()
    {
        return user_action_predicate_list;
    }

    public void addUser_action_predicate(Exp user_action_predicate)
    {
        this.user_action_predicate_list.add( user_action_predicate );
    }

}
