package com.surgical.decision3.common.bean.tool;

import org.simpleframework.xml.Attribute;
import org.simpleframework.xml.Element;

@Element (required=false)
public class PrepareSetTool extends BaseTool
{

	public PrepareSetTool()
	{
		
	}
	
	@Attribute (required=false)
	private String id;
	
	@Attribute (required=false)
	private String tool_name;
	
	@Attribute (required=false)
	private String toolNo;
	
	@Attribute (required=false)
	private String action;
	
	@Attribute (required=false)
	private String tool_id;
	
	@Attribute (required=false)
	private String prepare_set_id;
	
	@Attribute (required=false)
	private String tool_code;
	
	public String getTool_code()
	{
		return tool_code;
	}
	public void setTool_code(String tool_code)
	{
		this.tool_code = tool_code;
	}
	public String getTool_id()
	{
		return tool_id;
	}
	public void setTool_id(String tool_id)
	{
		this.tool_id = tool_id;
	}
	public String getPrepare_set_id()
	{
		return prepare_set_id;
	}
	public void setPrepare_set_id(String prepare_set_id)
	{
		this.prepare_set_id = prepare_set_id;
	}
	public String getId()
	{
		return id;
	}
	public void setId(String id)
	{
		this.id = id;
	}
	public String getTool_name()
	{
		return tool_name;
	}
	public void setTool_name(String tool_name)
	{
		this.tool_name = tool_name;
	}
	public String getToolNo()
	{
		return toolNo;
	}
	public void setToolNo(String toolNo)
	{
		this.toolNo = toolNo;
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
