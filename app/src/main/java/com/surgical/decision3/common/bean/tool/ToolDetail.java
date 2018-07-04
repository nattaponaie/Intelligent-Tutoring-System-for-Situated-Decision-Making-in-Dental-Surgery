package com.surgical.decision3.common.bean.tool;

import java.util.ArrayList;

import org.simpleframework.xml.Attribute;
import org.simpleframework.xml.ElementList;

public class ToolDetail
{
	@Attribute(required=false)
	private int id;	//model name
	
	@Attribute(required=false)
	private String name;	//model name
	
	@Attribute(required=false)
	private String code;	//model code
	
	@Attribute(required=false)
	private String tool_code;	//model code
	
	@Attribute(required=false)
	private String tool_number;		//number name to appear on the list
	
	@Attribute(required=false)
	private String tool_number2;	//number (second set of number)

	@ElementList(required=false)
	private ArrayList<Double> tool_length_list;
	
	@Attribute(required=false)
	private int tool_id;
	
	@Attribute(required=false)
	private String tool_name;
	
	@Attribute(required=false)
	private double tool_diameter_tip;

	@Attribute(required=false)
	private double tool_diameter_d1;
	
	@Attribute(required=false)
	private double AAE;	//has size of tool

	public String getName()
	{
		return name;
	}

	public void setName(String name)
	{
		this.name = name;
	}

	public String getCode()
	{
		return code;
	}

	public void setCode(String code)
	{
		this.code = code;
	}

	public String getTool_number()
	{
		return tool_number;
	}

	public void setTool_number(String tool_number)
	{
		this.tool_number = tool_number;
	}

	public String getTool_number2()
	{
		return tool_number2;
	}

	public void setTool_number2(String tool_number2)
	{
		this.tool_number2 = tool_number2;
	}

	public ArrayList<Double> getTool_length_list()
	{
		return tool_length_list;
	}

	public void setTool_length_list(ArrayList<Double> tool_length_list)
	{
		this.tool_length_list = tool_length_list;
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

	public double getTool_diameter_tip()
	{
		return tool_diameter_tip;
	}

	public void setTool_diameter_tip(double tool_diameter_tip)
	{
		this.tool_diameter_tip = tool_diameter_tip;
	}

	public double getTool_diameter_end()
	{
		return tool_diameter_d1;
	}

	public void setTool_diameter_end(double tool_diameter_d1)
	{
		this.tool_diameter_d1 = tool_diameter_d1;
	}

	public double isAAE()
	{
		return AAE;
	}

	public void setAAE(double aae)
	{
		AAE = aae;
	}

	public int getId()
	{
		return id;
	}

	public void setId(int id)
	{
		this.id = id;
	}

	public String getTool_code()
	{
		return tool_code;
	}

	public void setTool_code(String tool_code)
	{
		this.tool_code = tool_code;
	}
	
	
}
