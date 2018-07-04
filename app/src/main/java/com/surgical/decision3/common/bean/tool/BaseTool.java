package com.surgical.decision3.common.bean.tool;

import org.simpleframework.xml.Element;

public class BaseTool
{
//	@Attribute (name="class")
//	String toolClassName;
	
//-------------------------------------------
	@Element (required=false)
	String toolType;
	
	@Element (required=false)
	String toolName;
	
	public BaseTool()
	{
		super();
	}

	public String getToolType()
	{
		return toolType;
	}

	public void setToolType(String toolType)
	{
		this.toolType = toolType;
	}

	public String getToolName()
	{
		return toolName;
	}

	public void setToolName(String toolName)
	{
		this.toolName = toolName;
	}

//	public String getClassName()
//	{
//		return className;
//	}
//
//	public void setClassName(String className)
//	{
//		this.className = className;
//	}

	
	
}
