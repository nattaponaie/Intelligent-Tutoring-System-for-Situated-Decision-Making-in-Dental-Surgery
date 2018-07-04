package com.surgical.decision3.common.bean.intervention.action;

import java.util.ArrayList;

import org.simpleframework.xml.Attribute;
import org.simpleframework.xml.Element;
import org.simpleframework.xml.ElementList;
import org.simpleframework.xml.Root;

@Root
public class CommandActionStep
{

	public CommandActionStep()
	{
		super();
		commandActions = new ArrayList <CommandAction>();
	}
	
	public void addCommandAction( CommandAction action)
	{
		this.commandActions.add(action);
	}

	@Attribute
	private int stepNo;
	
	@Attribute
	private String stepName;
	
	@Element
	private String narration;
	
	@ElementList
	private ArrayList <CommandAction> commandActions;
	
	public int getStepNo()
	{
		return stepNo;
	}

	public void setStepNo(int stepNo)
	{
		this.stepNo = stepNo;
	}

	public String getStepName()
	{
		return stepName;
	}

	public ArrayList <CommandAction> getCommandActions()
	{
		return commandActions;
	}

	public void setCommandActions(ArrayList <CommandAction> commandActions)
	{
		this.commandActions = commandActions;
	}

	public void setStepName(String stepName)
	{
		this.stepName = stepName;
	}

	public String getNarration()
	{
		return narration;
	}

	public void setNarration(String narration)
	{
		this.narration = narration;
	}
}
