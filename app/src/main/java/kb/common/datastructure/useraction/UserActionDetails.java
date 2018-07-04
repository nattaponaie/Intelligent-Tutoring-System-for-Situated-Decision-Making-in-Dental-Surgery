package kb.common.datastructure.useraction;

import java.util.ArrayList;

/**
 * Created by Nattapon on 16/6/2560.
 */

public class UserActionDetails {

    public static ArrayList<ActionDetails> actionWithPredicate = new ArrayList<>();

    public static ArrayList<ActionDetails> getActionWithPredicate() {
        return actionWithPredicate;
    }

    public void setActionWithPredicate(ArrayList<ActionDetails> actionWithPredicate) {
        this.actionWithPredicate = actionWithPredicate;
    }

}
