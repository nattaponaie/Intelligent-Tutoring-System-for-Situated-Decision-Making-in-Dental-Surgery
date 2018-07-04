package kb.common.datastructure.graph;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import fr.uga.pddl4j.parser.Exp;
import kb.common.constants.GraphNodeConstants;
import kb.common.datastructure.oneofprojection.OneOfGroup;
import kb.common.datastructure.solutionpath.PlanAction;
import kb.common.datastructure.worldstate.State;
import kb.util.InferenceEngineUtil;

/**
 * Created by Nattapon on 16/7/2560.
 */

public class TutorGraphNode {

    String nodeType;

    List<TutorGraphNode> ParentNodes = null;
    List<TutorGraphNode> ChildNodes = null;

    boolean isDesiredOutCome = false;//for projection
    boolean isAllowExplore = false;
    boolean isMain = false;     //Mes added

    // Mes - ONEOF attributes
    boolean isOneOf = false;
    boolean isOneOfOccur = false;
    double oneOfProbability;

    OneOfGroup oneOfGroup;

    State state = null;

    PlanAction actionNode = null;

    Exp actionConditionExp = null;

    Exp projectionExp;

    Exp perceptionExp = null;

    Exp comprehensionExp = null;

    HashMap<String, String> perceptionWithComprehensionHashMap = new HashMap<>();

    HashMap<String, Exp> predicateExpDataHashMap = new HashMap<>();

    List<TutorGraphNode[]> perceptionWithComprehensionList = new ArrayList<>();

    List<TutorActionConditionDetails> acDetailsList = new ArrayList<>();

    public List<TutorActionConditionDetails> getAcDetailsList() {
        return acDetailsList;
    }

    public void setAcDetailsList(List<TutorActionConditionDetails> acDetailsList) {
        this.acDetailsList = acDetailsList;
    }

    public List<TutorGraphNode[]> getPerceptionWithComprehensionList() {
        return perceptionWithComprehensionList;
    }

    public void setPerceptionWithComprehensionList(List<TutorGraphNode[]> perceptionWithComprehensionList) {
        this.perceptionWithComprehensionList = perceptionWithComprehensionList;
    }

    public HashMap<String, Exp> getPredicateExpDataHashMap() {
        return predicateExpDataHashMap;
    }

    public void setPredicateExpDataHashMap(HashMap<String, Exp> predicateExpDataHashMap) {
        this.predicateExpDataHashMap = predicateExpDataHashMap;
    }

    public HashMap<String, String> getPerceptionWithComprehensionHashMap() {
        return perceptionWithComprehensionHashMap;
    }

    public void setPerceptionWithComprehensionHashMap(HashMap<String, String> perceptionWithComprehensionHashMap) {
        this.perceptionWithComprehensionHashMap = perceptionWithComprehensionHashMap;
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

    public Exp getProjectionExp() {
        return projectionExp;
    }

    public boolean isCompoundExp(Exp exp )
    {
        return InferenceEngineUtil.isCompoundPredicate( exp );
    }

    public void setProjectionExp(Exp projectionExp) {
        this.projectionExp = projectionExp;
    }

    public TutorGraphNode(String nodeType)
    {
        this.nodeType = nodeType;

        if(this.ParentNodes == null) this.ParentNodes = new ArrayList<>();
        if(this.ChildNodes == null) this.ChildNodes = new ArrayList<>();
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

    public List<TutorGraphNode> getParentNodes() {
        return ParentNodes;
    }

    public TutorGraphNode getCurrentParentNode()
    {
        TutorGraphNode parent = null;
        for(TutorGraphNode node : this.ParentNodes)
        {
            parent = node;
        }
        return parent;
    }

    public TutorGraphNode getCurrentChildNode()
    {
        TutorGraphNode child = null;
        for(TutorGraphNode node : this.ChildNodes)
        {
            child = node;
        }
        return child;
    }

    public void setParentNodes(List<TutorGraphNode> parentNodes) {
        this.ParentNodes = parentNodes;
    }

    public List<TutorGraphNode> getChildNodes() {
        return ChildNodes;
    }

    public void setChildNodes(List<TutorGraphNode> childNodes) {
        this.ChildNodes = childNodes;
    }

    public void addChildNode(TutorGraphNode child_node)
    {
        this.ChildNodes.add(child_node);
    }

    public void addParentNode(TutorGraphNode parent_node)
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
            //NOTE: if node type is ActionCondition, there should be the link to Perception and Comprehension.
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

    public boolean isMain() {
        return isMain;
    }

    public void setMain(boolean main) {
        isMain = main;
    }

    public boolean isOneOf() {
        return isOneOf;
    }

    public void setOneOf(boolean oneOf) {
        isOneOf = oneOf;
    }

    public boolean isOneOfOccur() {
        return isOneOfOccur;
    }

    public void setOneOfOccur(boolean oneOfOccur) {
        isOneOfOccur = oneOfOccur;
    }

    public double getOneOfProbability() {
        return oneOfProbability;
    }

    public void setOneOfProbability(double oneOfProbability) {
        this.oneOfProbability = oneOfProbability;
    }

    public OneOfGroup getOneOfGroup() {
        return oneOfGroup;
    }

    public void setOneOfGroup(OneOfGroup oneOfGroup) {
        this.oneOfGroup = oneOfGroup;
    }
}
