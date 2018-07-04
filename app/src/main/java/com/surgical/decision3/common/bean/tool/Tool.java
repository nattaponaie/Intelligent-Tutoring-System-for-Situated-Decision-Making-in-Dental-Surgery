package com.surgical.decision3.common.bean.tool;

import java.util.ArrayList;

import org.simpleframework.xml.Attribute;
import org.simpleframework.xml.ElementList;
	
//TODO: add following attributes
// isMaterial
// tool_length
//Tool Number
public class Tool extends BaseTool
{
	@Attribute(required=false)
	private int id;
	
	@Attribute(required=false)
	private String name;
	
	@Attribute(required=false)
	private String desc;
	
	@Attribute(required=false)
	private int tool_type_id;
	
	@Attribute(required=false)
	private String tool_type_code;
	
	@Attribute(required=false)
	private String code;
	
	@Attribute(required=false)
	private boolean hasNumber;	//has size of tool
	
	@Attribute(required=false)
	private boolean isMaterial;	//has size of tool
	
	@Attribute(required=false)
	private String functionality;
	
	@ElementList (required=false)
	ArrayList<Action> actions;
	
	@ElementList (required=false)
	ArrayList<Set> sets;
	
	@ElementList (required=false)
	ArrayList<ToolDetail> toolDetails;
	
	public ArrayList<Set> getSets()
	{
		return sets;
	}

	public void setSets(ArrayList<Set> sets)
	{
		this.sets = sets;
	}

	public ArrayList<Action> getActions()
	{
		return actions;
	}

	public void setActions(ArrayList<Action> actions)
	{
		this.actions = actions;
	}

	public String getFunctionality()
	{
		return functionality;
	}

	public void setFunctionality(String functionality)
	{
		this.functionality = functionality;
	}

	public Tool()
	{
		super();
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

	public String getDesc()
	{
		return desc;
	}

	public void setDesc(String desc)
	{
		this.desc = desc;
	}

	public int getTool_type_id()
	{
		return tool_type_id;
	}

	public void setTool_type_id(int tool_type_id)
	{
		this.tool_type_id = tool_type_id;
	}

	public String getTool_type_code()
	{
		return tool_type_code;
	}

	public void setTool_type_code(String tool_type_code)
	{
		this.tool_type_code = tool_type_code;
	}

	public String getCode()
	{
		return code;
	}

	public void setCode(String code)
	{
		this.code = code;
	}

	public boolean isHasNumber()
	{
		return hasNumber;
	}

	public void setHasNumber(boolean hasNumber)
	{
		this.hasNumber = hasNumber;
	}

	public boolean isMaterial()
	{
		return isMaterial;
	}

	public void setIsMaterial(boolean isMaterial)
	{
		this.isMaterial = isMaterial;
	}

	public ArrayList<ToolDetail> getToolDetails()
	{
		return toolDetails;
	}

	public void setToolDetails(ArrayList<ToolDetail> toolDetails)
	{
		this.toolDetails = toolDetails;
	}
}
