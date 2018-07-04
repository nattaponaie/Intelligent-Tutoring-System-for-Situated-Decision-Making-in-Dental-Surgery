package kb.common.datastructure.useraction;

/**
 * Created by Nattapon on 20/6/2560.
 */

public class ActionDetails {

    String predicate;
    String value;
    boolean isNegative = false;

    public String getPredicate() {
        return predicate;
    }

    public void setPredicate(String predicate) {
        this.predicate = predicate;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public boolean isNegative() {
        return isNegative;
    }

    public void setNegative(boolean negative) {
        this.isNegative = negative;
    }
}
