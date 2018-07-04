package kb.common.datastructure.graph;

import java.util.ArrayList;
import java.util.List;

import kb.common.constants.GraphNodeConstants;

/**
 * Created by Nattapon on 16/7/2560.
 */

public class StudentGraph {

    StudentGraphNode stateNode = new StudentGraphNode(GraphNodeConstants.NODE_TYPE_STATE);
    List<StudentGraphNode> PerceptionList = new ArrayList<>();
    List<StudentGraphNode> ComprehensionList = new ArrayList<>();
    List<StudentGraphNode> ActionConditionList = new ArrayList<>();
    List<StudentGraphNode> ProjectionList = new ArrayList<>();

    StudentGraphNode actionNode = new StudentGraphNode(GraphNodeConstants.NODE_TYPE_ACTION);

    StudentGraphNode endNode = new StudentGraphNode(GraphNodeConstants.NODE_TYPE_END);

    public List<StudentGraphNode> getPerceptionList() {
        return PerceptionList;
    }

    public void setPerceptionList(List<StudentGraphNode> PerceptionList) {
        this.PerceptionList = PerceptionList;
    }

    public List<StudentGraphNode> getComprehensionList() {
        return ComprehensionList;
    }

    public void setComprehensionList(List<StudentGraphNode> ComprehensionList) {
        this.ComprehensionList = ComprehensionList;
    }

    public StudentGraphNode getEndNode() {
        return endNode;
    }

    public void setEndNode(StudentGraphNode endNode) {
        this.endNode = endNode;
    }

    public StudentGraphNode getActionNode() {
        return actionNode;
    }

    public void setActionNode(StudentGraphNode actionNode) {
        this.actionNode = actionNode;
    }

    public StudentGraphNode getStateNode() {
        return stateNode;
    }

    public void setStateNode(StudentGraphNode stateNode) {
        this.stateNode = stateNode;
    }

    public List<StudentGraphNode> getActionConditionList() {
        return ActionConditionList;
    }

    public void setActionConditionList(List<StudentGraphNode> actionConditionList) {
        this.ActionConditionList = actionConditionList;
    }

    public List<StudentGraphNode> getProjectionList() {
        return ProjectionList;
    }

    public void setProjectionList(List<StudentGraphNode> projectionList) {
        this.ProjectionList = projectionList;
    }


    public StudentGraphNode getProjectNodeThatDoesNotSatisfyDO()
    {
        StudentGraphNode pjNode = null;

        for(StudentGraphNode stNode  : this.getProjectionList() )
        {
            if(stNode.nodeType.equals( GraphNodeConstants.NODE_TYPE_PROJECTION )
               && stNode.isDesiredOutCome() == false )
            {
                pjNode = stNode;
                break;
            }
        }

        return pjNode;
    }

    public StudentGraphNode getProjectionNodeByDesiredOutcome(boolean isSatisfyDO)
    {
        for( StudentGraphNode pjNode : this.getProjectionList() )
        {
            if(pjNode.nodeType.equals( GraphNodeConstants.NODE_TYPE_PROJECTION )
                    && pjNode.isDesiredOutCome() == isSatisfyDO )
            {
                //When getting the node, if it is in the exclude list, get the new one.
                //If it is not in the exclude list, use this node.
                return pjNode;
            }
        }
        return null;
    }

}
