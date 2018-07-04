package com.surgical.decision3.common.bean.tool;

import java.util.ArrayList;

import org.simpleframework.xml.ElementList;
import org.simpleframework.xml.Root;

@Root
public class ToolTray
{
	public ToolTray()
	{
		super();
		tools = new ArrayList <BaseTool>();
	}
	
	@ElementList (required=false)
	ArrayList <BaseTool> tools;
	
	public void addTool( BaseTool tool )
	{
		this.tools.add( tool );
	}

	public ArrayList <BaseTool> getTools()
	{
		return tools;
	}

	public void setTools(ArrayList <BaseTool> tools)
	{
		this.tools = tools;
	}
}
