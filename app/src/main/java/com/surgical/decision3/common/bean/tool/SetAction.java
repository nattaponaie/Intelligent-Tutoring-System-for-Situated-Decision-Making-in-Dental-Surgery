package com.surgical.decision3.common.bean.tool;

import org.simpleframework.xml.Attribute;

public class SetAction
{
	@Attribute
	int id;
	
	@Attribute
	int set_id;
	
	@Attribute
	String set_name;
	
	@Attribute
	int action_id;
	
	@Attribute
	String action_name;
	
	@Attribute
	String action_code;

	public int getId()
	{
		return id;
	}

	public void setId(int id)
	{
		this.id = id;
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

	public int getAction_id()
	{
		return action_id;
	}

	public void setAction_id(int action_id)
	{
		this.action_id = action_id;
	}

	public String getAction_name()
	{
		return action_name;
	}

	public void setAction_name(String action_name)
	{
		this.action_name = action_name;
	}

	public String getAction_code()
	{
		return action_code;
	}

	public void setAction_code(String action_code)
	{
		this.action_code = action_code;
	}
	
	
}
