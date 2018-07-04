package com.surgical.decision3.common.bean.tool;

import org.simpleframework.xml.Attribute;
import org.simpleframework.xml.ElementList;

import java.util.ArrayList;

//Set of tool. 
public class Set
{
	@Attribute (required=false)
	int id;
	
	@Attribute (required=false)
	String name;
	
	@Attribute (required=false)
	String description;
	
	@Attribute (required=false)
	String code;
	
	@ElementList (required=false)
	ArrayList<Action> actions;
	
	@ElementList (required=false)
	ArrayList<ToolSet> associates;	// just simple tool "getAllTools_simple"
	
	

	public ArrayList<ToolSet> getAssociates()
	{
		return associates;
	}

	public void setAssociates(ArrayList<ToolSet> associatedTools)
	{
		this.associates = associatedTools;
	}

	public ArrayList<Action> getActions()
	{
		return actions;
	}

	public void setActions(ArrayList<Action> actions)
	{
		this.actions = actions;
	}

	public int getId()
	{
		return id;
	}

	public void setId(int id)
	{
		this.id = id;
	}

	public String getName()
	{
		return name;
	}

	public void setName(String name)
	{
		this.name = name;
	}

	public String getDescription()
	{
		return description;
	}

	public void setDescription(String description)
	{
		this.description = description;
	}

	public String getCode()
	{
		return code;
	}

	public void setCode(String code)
	{
		this.code = code;
	}
	
	
}
