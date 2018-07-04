package com.surgical.decision3.common.bean.tool;

import java.util.ArrayList;

import org.simpleframework.xml.ElementList;
import org.simpleframework.xml.Root;

@Root
public class SelectedToolTray
{
	@ElementList (required=false)
	ArrayList <BaseTool> selectedTools;

	public SelectedToolTray()
	{
		super();
		this.selectedTools = new ArrayList<BaseTool>();
	}
	
	public void addTool( BaseTool tool )
	{
		this.selectedTools.add( tool );
	}

	public ArrayList<BaseTool> getSelectedTools()
	{
		return selectedTools;
	}

	public void setSelectedTools(ArrayList<BaseTool> selectedTools)
	{
		this.selectedTools = selectedTools;
	}
}
