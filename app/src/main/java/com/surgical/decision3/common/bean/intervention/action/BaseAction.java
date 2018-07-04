package com.surgical.decision3.common.bean.intervention.action;

import org.simpleframework.xml.Element;

public class BaseAction {
	
	public BaseAction()
	{
		super();
	}
	
	public BaseAction(String actionDescription, String actionCode,
			String semanticActionCode, BaseActionObject onObject,
			BaseActionObject toObject)
	{
		super();
		this.actionDescription = actionDescription;
		this.actionCode = actionCode;
		this.semanticActionCode = semanticActionCode;
		this.onObject = onObject;
		this.toObject = toObject;
	}

	@Element(name = "action_desc")
	String actionDescription;

	@Element(name = "action_code")
	String actionCode;

	@Element(name = "semantic_action_code")
	String semanticActionCode;

	@Element(name = "onObject", required=false)
	BaseActionObject onObject;

	@Element(name = "toObject", required=false)
	BaseActionObject toObject;

	public String getActionDescription()
	{
		return actionDescription;
	}

	public void setActionDescription(String actionDescription)
	{
		this.actionDescription = actionDescription;
	}

	public String getActionCode()
	{
		return actionCode;
	}

	public void setActionCode(String actionCode)
	{
		this.actionCode = actionCode;
	}

	public String getSemanticActionCode()
	{
		return semanticActionCode;
	}

	public void setSemanticActionCode(String semanticActionCode)
	{
		this.semanticActionCode = semanticActionCode;
	}

	public BaseActionObject getOnObject()
	{
		return onObject;
	}

	public void setOnObject(BaseActionObject onObject)
	{
		this.onObject = onObject;
	}

	public BaseActionObject getToObject()
	{
		return toObject;
	}

	public void setToObject(CommandActionObject toObject)
	{
		this.toObject = toObject;
	}

}
