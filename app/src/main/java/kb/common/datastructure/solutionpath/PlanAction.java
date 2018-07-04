package kb.common.datastructure.solutionpath;

import java.util.ArrayList;

import fr.uga.pddl4j.parser.Exp;

/**
 * This is for Plan.pdl only
 * @author mess
 *
 */

public class PlanAction {
	/**
	 *  Action name
	 */
	private String name;
	
	private Plan parentPlan;
	
	/**
	 * Parent and children must be read sequentially
	 */
	private PlanAction parent;
	
	private PlanAction child;
	
	private boolean isConditionalAction;
	
	private Exp actionCondition;
	
	public enum ConditionType {
		/**
		 *  for while() loop
		 */
		LOOP,
		/**
		 *  for if()
		 */
		BRANCH
	}
	
	private ConditionType actionConditionType;
	
	private ArrayList<PlanAction> subActionList;

	private ArrayList<PlanAction> subActionElseList;
	
	private boolean isSubAction;
	
	private PlanAction parentSubAction;

	/**
	 *  Desired outcomes of the action
	 */
	private Exp desired_outcomes;
	
	public PlanAction(){
		this.isConditionalAction = false;
		this.subActionList = new ArrayList<PlanAction>();
		this.subActionElseList = new ArrayList<PlanAction>();
	}
	
	public PlanAction(String actionName, Exp desired_outcomes, Plan parentPlan){
		this();
		this.name = actionName;
		this.desired_outcomes = desired_outcomes;
		this.parentPlan = parentPlan;
	}

	public String getActionName() {
		return name;
	}

	public void setActionName(String actionName) {
		this.name = actionName;
	}

	public Exp getDesired_outcomes() {
		return desired_outcomes;
	}

	public void setDesired_outcomes(Exp desired_outcomes) {
		this.desired_outcomes = desired_outcomes;
	}
	
/*	public void addPrecondition(Symbol precondition){
		this.precondition.add(precondition);
	}*/

	public Plan getParentPlan() {
		return parentPlan;
	}

	public void setParentPlan(Plan parentPlan) {
		this.parentPlan = parentPlan;
	}

	public PlanAction getParent() {
		return this.parent;
	}

	public void setParent(PlanAction parent) {
		this.parent = parent;
	}

	public PlanAction getChild() {
		return child;
	}

	public void setChild(PlanAction child) {
		this.child = child;
	}
	
	public String toString(){
		return this.name.toString();
	}

	public boolean isConditionalAction() {
		return isConditionalAction;
	}

	public void setConditionalAction(boolean isConditionalAction) {
		this.isConditionalAction = isConditionalAction;
	}

	public Exp getActionCondition() {
		return actionCondition;
	}

	public void setActionCondition(Exp actionCondition) {
		this.actionCondition = actionCondition;
	}

	public ConditionType getActionConditionType() {
		return actionConditionType;
	}

	public void setActionConditionType(ConditionType actionConditionType) {
		this.actionConditionType = actionConditionType;
	}

	public ArrayList<PlanAction> getSubActionList() {
		return subActionList;
	}

	public void setSubActionList(ArrayList<PlanAction> subActionList) {
		this.subActionList = subActionList;
	}

	public ArrayList<PlanAction> getSubActionElseList() {
		return subActionElseList;
	}

	public void setSubActionElseList(ArrayList<PlanAction> subActionElseList) {
		this.subActionElseList = subActionElseList;
	}

	public PlanAction getParentSubAction() {
		return parentSubAction;
	}

	public void setParentSubAction(PlanAction parentSubAction) {
		this.parentSubAction = parentSubAction;
	}

	public boolean isSubAction() {
		return isSubAction;
	}

	public void setSubAction(boolean isSubAction) {
		this.isSubAction = isSubAction;
	}
}
