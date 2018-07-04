package com.surgical.decision3.common.bean.tool;

import java.util.ArrayList;

import org.simpleframework.xml.Attribute;
import org.simpleframework.xml.Element;
import org.simpleframework.xml.ElementList;

@Element (required=false)
public class PrepareSet
{
	@Attribute (required=false)
	private String id;
	
	@Attribute (required=false)
	private String name;
	
	@Attribute (required=false)
	private String description;
	
	@ElementList (required=false)
	private ArrayList<PrepareSetTool> prepareSetTools;
	
	@Attribute (required=false)
	private String action;
	
	@Attribute (required=false)
	private String code;
	
	@Attribute (required=false)
	private double prepareLength;
	
	@Attribute (required=false)
	private boolean isToToolTray; 
	
	public double getPrepareLength()
	{
		return prepareLength;
	}

	public void setPrepareLength(double prepareLength)
	{
		this.prepareLength = prepareLength;
	}

	public boolean isToToolTray()
	{
		return isToToolTray;
	}

	public void setToToolTray(boolean isToToolTray)
	{
		this.isToToolTray = isToToolTray;
	}

	public String getCode()
	{
		return code;
	}

	public void setCode(String code)
	{
		this.code = code;
	}

	public String getAction()
	{
		return action;
	}

	public void setAction(String action)
	{
		this.action = action;
	}

	public PrepareSet()
	{
		this.prepareSetTools = new ArrayList<PrepareSetTool>();
	}

	public ArrayList<PrepareSetTool> getSetTools()
	{
		return prepareSetTools;
	}

	public void setSetTools(ArrayList<PrepareSetTool> setTools)
	{
		this.prepareSetTools = setTools;
	}

	public String getId()
	{
		return id;
	}

	public void setId(String id)
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

	public ArrayList<PrepareSetTool> getPrepareSetTools()
	{
		return prepareSetTools;
	}

	public void setPrepareSetTools(ArrayList<PrepareSetTool> prepareSetTools)
	{
		this.prepareSetTools = prepareSetTools;
	}
	
	
}
