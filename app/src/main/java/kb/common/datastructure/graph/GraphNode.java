package kb.common.datastructure.graph;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import fr.uga.pddl4j.parser.Exp;
import kb.common.constants.GraphNodeConstants;
import kb.common.datastructure.solutionpath.PlanAction;
import kb.common.datastructure.worldstate.State;

/**
 * Created by Nattapon on 6/7/2560.
 */

public class GraphNode {

    String nodeType;

    List<GraphNode> ParentNodes = null;
    List<GraphNode> ChildNodes = null;

    boolean isDesiredOutCome = false;//for projection
    boolean isAllowExplore = false;
    boolean isOccurred = false;
    boolean isPerceivable = false;

    State state = null;

    PlanAction actionNode = null;

    Exp actionConditionExp = null;

    String actionConditionName;

    Exp projectionExp;

    Exp perceptionExp = null;

    Exp comprehensionExp = null;

    boolean isSatisfyDesiredOutcome = false;//for checking DO after student submit action

    boolean isSatisfyGoal = false;

    boolean isInvalidAction = false;

    HashMap<String, String> perceptionWithComprehensionString = new HashMap<>();

    HashMap<String, Exp> predicateExpData = new HashMap<>();

    public HashMap<String, Exp> getPredicateExpData() {
        return predicateExpData;
    }

    public void setPredicateExpData(HashMap<String, Exp> predicateExpData) {
        this.predicateExpData = predicateExpData;
    }

    public HashMap<String, String> getPerceptionWithComprehensionString() {
        return perceptionWithComprehensionString;
    }

    public void setPerceptionWithComprehensionString(HashMap<String, String> perceptionWithComprehensionString) {
        this.perceptionWithComprehensionString = perceptionWithComprehensionString;
    }

    public boolean isInvalidAction() {
        return isInvalidAction;
    }

    public void setInvalidAction(boolean invalidAction) {
        isInvalidAction = invalidAction;
    }

    public boolean isSatisfyDesiredOutcome() {
        return isSatisfyDesiredOutcome;
    }

    public void setSatisfyDesiredOutcome(boolean satisfyDesiredOutcome) {
        this.isSatisfyDesiredOutcome = satisfyDesiredOutcome;
    }

    public boolean isSatisfyGoal() {
        return isSatisfyGoal;
    }

    public void setSatisfyGoal(boolean isSatisfyGoal) {
        this.isSatisfyGoal = isSatisfyGoal;
    }

    public Exp getPerceptionExp() {
        return perceptionExp;
    }

    public void setPerceptionExp(Exp perceptionExp) {
        this.perceptionExp = perceptionExp;
    }

    public Exp getComprehensionExp() {
        return comprehensionExp;
    }

    public void setComprehensionExp(Exp comprehensionExp) {
        this.comprehensionExp = comprehensionExp;
    }

    public String getActionConditionName() {
        return actionConditionName;
    }

    public void setActionConditionName(String actionConditionName) {
        this.actionConditionName = actionConditionName;
    }

    public Exp getProjectionExp() {
        return projectionExp;
    }

    public void setProjectionExp(Exp projectionExp) {
        this.projectionExp = projectionExp;
    }

    public GraphNode(String nodeType)
    {
        this.nodeType = nodeType;

        if(this.ParentNodes == null) this.ParentNodes = new ArrayList<>();
        if(this.ChildNodes == null) this.ChildNodes = new ArrayList<>();
    }

    public boolean isPerceivable() {
        return isPerceivable;
    }

    public void setPerceivable(boolean perceivable) {
        isPerceivable = perceivable;
    }

    public boolean isOccurred() {
        return isOccurred;
    }

    public void setOccurred(boolean occurred) {
        isOccurred = occurred;
    }

    public Exp getActionCondition() {
        return actionConditionExp;
    }

    public void setActionCondition(Exp actionConditionExp) {
        this.actionConditionExp = actionConditionExp;
    }

    public PlanAction getActionNode() {
        return actionNode;
    }

    public void setActionNode(PlanAction Action) {
        this.actionNode = Action;
    }

    public State getState() {
        return state;
    }

    public void setState(State stateNode) {
        this.state = stateNode;
    }

    public String getNodeType() {
        return nodeType;
    }

    public void setNodeType(String nodeType) {
        this.nodeType = nodeType;
    }

    public List<GraphNode> getParentNodes() {
        return ParentNodes;
    }

    public GraphNode getCurrentParentNode()
    {
        GraphNode parent = null;
        for(GraphNode node : this.ParentNodes)
        {
            parent = node;
        }
        return parent;
    }

    public GraphNode getCurrentChildNode()
    {
        GraphNode child = null;
        for(GraphNode node : this.ChildNodes)
        {
            child = node;
        }
        return child;
    }

    public void setParentNodes(List<GraphNode> parentNodes) {
        this.ParentNodes = parentNodes;
    }

    public List<GraphNode> getChildNodes() {
        return ChildNodes;
    }

    public void setChildNodes(List<GraphNode> childNodes) {
        this.ChildNodes = childNodes;
    }

    public void addChildNode(GraphNode child_node)
    {
        this.ChildNodes.add(child_node);
    }

    public void addParentNode(GraphNode parent_node)
    {
        this.ParentNodes.add(parent_node);
    }

    public boolean isDesiredOutCome() {//for projection
        return isDesiredOutCome;
    }

    public void setDesiredOutCome(boolean desiredOutCome) {//for projection
        this.isDesiredOutCome = desiredOutCome;
    }

    public boolean isAllowExplore() {
        return isAllowExplore;
    }

    public void setAllowExplore(boolean allowExplore) {
        this.isAllowExplore = allowExplore;
    }



    public Exp getExpByNodeType()
    {
        Exp exp = null;
        if( this.nodeType.equals( GraphNodeConstants.NODE_TYPE_ACTION ) )
        {
            // do nothing
        }
        else if( this.nodeType.equals( GraphNodeConstants.NODE_TYPE_ACTION_CONDITION ) )
        {
            exp = this.actionConditionExp;
        }
        else if( this.nodeType.equals( GraphNodeConstants.NODE_TYPE_PROJECTION ) )
        {
            exp = this.projectionExp;
        }
        else if( this.nodeType.equals( GraphNodeConstants.NODE_TYPE_STATE ) )
        {
            //do nothing
        }
        else if( this.nodeType.equals( GraphNodeConstants.NODE_TYPE_END) )
        {
            //do nothing
        }
        else if( this.nodeType.equals( GraphNodeConstants.NODE_TYPE_PERCEPTION) )
        {
            exp = this.perceptionExp;
        }
        else if( this.nodeType.equals( GraphNodeConstants.NODE_TYPE_COMPREHENSION) )
        {
            exp = this.comprehensionExp;
        }

        return exp;
    }

}
