package com.surgical.decision3.common.bean.tool;

import org.simpleframework.xml.Attribute;

public class ToolSet
{
	@Attribute(required=false)
	int id;
	
	@Attribute(required=false)
	int tool_id;
	
	@Attribute(required=false)
	String tool_name;
	
	@Attribute(required=false)
	int ref_set_id;
	
	@Attribute(required=false)
	int set_id;
	
	@Attribute(required=false)
	String set_name;
	
	@Attribute(required=false)
	String set_code;

	public int getId()
	{
		return id;
	}

	public void setId(int id)
	{
		this.id = id;
	}

	public int getTool_id()
	{
		return tool_id;
	}

	public void setTool_id(int tool_id)
	{
		this.tool_id = tool_id;
	}

	public String getTool_name()
	{
		return tool_name;
	}

	public void setTool_name(String tool_name)
	{
		this.tool_name = tool_name;
	}

	public int getRef_set_id()
	{
		return ref_set_id;
	}

	public void setRef_set_id(int ref_set_id)
	{
		this.ref_set_id = ref_set_id;
	}

	public int getSet_id()
	{
		return set_id;
	}

	public void setSet_id(int set_id)
	{
		this.set_id = set_id;
	}

	public String getSet_name()
	{
		return set_name;
	}

	public void setSet_name(String set_name)
	{
		this.set_name = set_name;
	}

	public String getSet_code()
	{
		return set_code;
	}

	public void setSet_code(String set_code)
	{
		this.set_code = set_code;
	}
	
}
