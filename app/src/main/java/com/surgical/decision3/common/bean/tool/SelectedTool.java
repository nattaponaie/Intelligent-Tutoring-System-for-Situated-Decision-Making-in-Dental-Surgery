package com.surgical.decision3.common.bean.tool;

import java.util.ArrayList;

import org.simpleframework.xml.Attribute;
import org.simpleframework.xml.Element;
import org.simpleframework.xml.ElementList;
import org.simpleframework.xml.Root;

@Root
public class SelectedTool extends BaseTool
{
	@Attribute (required=false)
	double diameter;

	@ElementList (required=false)
	ArrayList<ToolAction> toolActions;
	
	//NOTE: TOOL OBJECT IS FOR INTERVENTION.
	@Element (required=false)
	Object toolObject;
	
	@Element (required=false)
	String toolNo;
	
	@Attribute (required=false)
	int amount;
	
	@Attribute (required=false)
	String handle;
	
	@Attribute (required=false)
	boolean isSelected;
	
	@Attribute (required=false)
	String tool_code;
	

	@ElementList (required=false)
	ArrayList<Product> products;
	
	public String getToolCode()
	{
		return tool_code;
	}

	public void setToolCode(String toolCode)
	{
		this.tool_code = toolCode;
	}

	public double getDiameter()
	{
		return diameter;
	}

	public void setDiameter(double diameter)
	{
		this.diameter = diameter;
	}

	public String getToolNo()
	{
		return toolNo;
	}

	public void setToolNo(String toolNo)
	{
		this.toolNo = toolNo;
	}

	public ArrayList<Product> getProducts()
	{
		return products;
	}

	public void setProducts(ArrayList<Product> products)
	{
		this.products = products;
	}

	public Object getToolObject()
	{
		return toolObject;
	}

	public void setToolObject(Object toolObject)
	{
		this.toolObject = toolObject;
	}

	public SelectedTool(int amount, String handle,
			String toolAction, boolean isSelected)
	{
		super();
		this.amount = amount;
		this.handle = handle;
		this.isSelected = isSelected;
		
		this.toolActions = new ArrayList<ToolAction>();
		toolActions.add( new ToolAction( toolAction ) );
	}

	public SelectedTool(int amount, String handle, boolean isSelected,
			ArrayList<ToolAction> toolActions)
	{
		super();
		this.amount = amount;
		this.handle = handle;
		this.isSelected = isSelected;
		
		this.toolActions = (toolActions == null) ? new ArrayList<ToolAction>() : toolActions;
	}

	public SelectedTool()
	{
		super();
		
		this.isSelected = false;
		this.toolActions = new ArrayList<ToolAction>();
		this.products = new ArrayList<Product>();
	}
	
	public void addToolAction( String action )
	{
		this.toolActions.add( new ToolAction( action ) );
	}

	public int getAmount()
	{
		return amount;
	}

	public void setAmount(int amount)
	{
		this.amount = amount;
	}

	public String getHandle()
	{
		return handle;
	}

	public void setHandle(String handle)
	{
		this.handle = handle;
	}

	public ArrayList<ToolAction> getToolActions()
	{
		return toolActions;
	}

	public void setToolActions(ArrayList<ToolAction> toolActions)
	{
		this.toolActions = toolActions;
	}
	
	public boolean isSelected()
	{
		return isSelected;
	}

	public void setSelected(boolean isSelected)
	{
		this.isSelected = isSelected;
	}
}
