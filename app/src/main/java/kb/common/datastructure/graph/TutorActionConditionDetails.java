package kb.common.datastructure.graph;

import fr.uga.pddl4j.parser.Exp;

/**
 * Created by Nattapon on 3/8/2560.
 */

public class TutorActionConditionDetails {

    Exp exp = null;
    String type = "";
    TutorGraphNode refGraphNode = null;

    public Exp getExp() {
        return exp;
    }

    public void setExp(Exp exp) {
        this.exp = exp;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public TutorGraphNode getRefGraphNode() {
        return refGraphNode;
    }

    public void setRefGraphNode(TutorGraphNode refGraphNode) {
        this.refGraphNode = refGraphNode;
    }
}
