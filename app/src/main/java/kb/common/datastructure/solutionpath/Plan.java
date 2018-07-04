package kb.common.datastructure.solutionpath;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import fr.uga.pddl4j.exceptions.FatalException;
import fr.uga.pddl4j.parser.Exp;
import fr.uga.pddl4j.parser.Symbol;

/**
 * This class implements plan read by parser
 * @author mess
 *
 * @version 1.0
 */


public class Plan implements Serializable{
	
	private static final long serialVersionUID = 1L;
	/**
	 * Name of the procedure. ***** In Symbol object, use name.image to get String format
	 */
	private Symbol name;
	
	private Symbol domain;
	
	private Symbol problem;
	
	private Plan parentPlan;
	/**
	 *  Sequence of subplans
	 */
	private List<Plan> subplan;		//subplan objects are ordered sequentially.
	/**
	 * sequence of planActionList
	 */
	private List<PlanAction> planActionList;		//PlanAction objects are ordered sequentially
	
	private List<Exp> initialVariables;
	
	private Exp optionalCondition;

	public Plan() {
		this.subplan = new ArrayList<Plan>();
		this.planActionList = new ArrayList<PlanAction>();
		this.initialVariables = new ArrayList<Exp>();
	}
	public Plan(final Symbol name) {
        this();
        if (name == null) {
            throw new NullPointerException();
        }
        this.setName(name);
	}
	
	public String toString(){
		StringBuilder str = new StringBuilder();
        str.append("(define (plan ").append(this.name).append(")");
        str.append(")");
        return str.toString();
	}
	
	public Symbol getName() {
		return name;
	}
	public void setName(Symbol name) {
		this.name = name;
	}
	public Symbol getDomain() {
		return domain;
	}
	public void setDomain(Symbol domain) {
		this.domain = domain;
	}
	public Symbol getProblem() {
		return problem;
	}
	public void setProblem(Symbol problem) {
		this.problem = problem;
	}
	public List<Exp> getInitialVariables() {
		return initialVariables;
	}
	public void setInitialVariables(List<Exp> initialVariables) {
		this.initialVariables = initialVariables;
	}
    public final boolean addInitialVariables(final Exp variable) {
        if (variable == null) {
            throw new NullPointerException();
        }
        return this.initialVariables.add(variable);
    }
	public Plan getParentPlan() {
		return parentPlan;
	}
	public void setParentPlan(Plan parentPlan) {
		this.parentPlan = parentPlan;
	}
	public List<Plan> getSubplan() {
		return subplan;
	}
	public void addSubplan(Plan plan){
		if(plan == null){
			throw new FatalException("exp can not be null in addSubplan call");
		}
		this.subplan.add(plan);
	}
	public void setSubplan(List<Plan> subplan) {
		this.subplan = subplan;
	}
	public List<PlanAction> getPlanActionList() {
		return planActionList;
	}
	public void setPlanActionList(List<PlanAction> planActionList) {
		this.planActionList = planActionList;
	}
	public void addPlanAction(PlanAction tutorAction){
		this.planActionList.add(tutorAction);
	}



	/**
	 * Mes - Utility
	 * This must be executed in each subplan
	 */
	public void validatePlanActions(){
		/*for(PlanAction p : this.planActionList){
			if(p.getChildren().size() > 1){
				p.setBranchChildren(true);
			}
			if(p.getParents().size() > 1){
				p.setBranchParent(true);
			}
		}*/
	}
	
	/**
	 * Mes - get all planActionList in the main plan
	 *  *** work only in main_plan ***
	 */
	public List<PlanAction> getAllPlanActions(){
		if(this.subplan.isEmpty()){
			System.out.println("The instantiator is not a main plan. Please execute getAllPlanActions by using mainplan");
			return null;
		}
		List<PlanAction> pActionList = new ArrayList<PlanAction>();
		for(Plan p : this.getSubplan()){
			pActionList.addAll(p.getPlanActionList());
		}
		return pActionList;
	}
	
	/**
	 *  Mes - get only specific action by action name.
	 *  if it is execute in main plan, it will get all tutor actions.
	 */
	public PlanAction findActionByName(String actionName){
		List<PlanAction> pActionList = new ArrayList<PlanAction>();
		
		if(this.subplan.isEmpty()){
			pActionList = this.planActionList;
		} else {
			pActionList = this.getAllPlanActions();
		}
		for(PlanAction t : pActionList){
			if(t.getActionName().equals(actionName)){
				return t;
			}
		}
		System.err.println("Action name \"" + actionName + "\" is not found in " + this.name);
		
		return null;
		
	}
	public Exp getOptionalCondition() {
		return optionalCondition;
	}
	public void setOptionalCondition(Exp optionalCondition) {
		this.optionalCondition = optionalCondition;
	}

}
