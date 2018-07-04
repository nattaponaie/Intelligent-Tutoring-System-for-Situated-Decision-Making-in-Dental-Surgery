package kb.common.datastructure.oneofprojection;

import fr.uga.pddl4j.parser.Exp;

/**
 * Created by mess on 8/20/2017.
 */

public class OneOfNode {
    Exp exp;
    double probabilityValue;
    boolean isOneOfOccur;

    public Exp getExp() {
        return exp;
    }

    public void setExp(Exp exp) {
        this.exp = exp;
    }

    public double getProbabilityValue() {
        return probabilityValue;
    }

    public void setProbabilityValue(double probabilityValue) {
        this.probabilityValue = probabilityValue;
    }

    public boolean isOneOfOccur() {
        return isOneOfOccur;
    }

    public void setOneOfOccur(boolean oneOfOccur) {
        isOneOfOccur = oneOfOccur;
    }
}
