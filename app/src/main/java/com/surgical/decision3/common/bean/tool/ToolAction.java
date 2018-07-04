package com.surgical.decision3.common.bean.tool;

import org.simpleframework.xml.Root;
import org.simpleframework.xml.Text;

@Root
public class ToolAction
{
	@Text
	String action;
	
	public ToolAction()
	{
		super();
	}

	public ToolAction(String action)
	{
		super();
		this.action = action;
	}

	public String getAction()
	{
		return action;
	}

	public void setAction(String action)
	{
		this.action = action;
	}
}
