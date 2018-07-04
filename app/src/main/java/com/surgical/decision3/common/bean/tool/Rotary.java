package com.surgical.decision3.common.bean.tool;

import java.util.ArrayList;

import org.simpleframework.xml.Attribute;

public class Rotary
{

	@Attribute
	int id;

	@Attribute(required = false)
	String product_id;

	@Attribute(required = false)
	String head_no;

	@Attribute(required = false)
	double diameter;

	int tool_details_ref;
	String task;

	@Attribute(required = false)
	ArrayList<Double> available_lengths;

	String is_recommended;

	@Attribute(required = false)
	String model;

	@Attribute(required = false)
	String model_code;

	String percentage_taper_from_tip;
	int tool_id;
	int tool_type_id;

	public ArrayList<Double> getAvailable_lengths()
	{
		return available_lengths;
	}

	public void setAvailable_lengths(ArrayList<Double> available_lengths)
	{
		this.available_lengths = available_lengths;
	}

	public int getId()
	{
		return id;
	}

	public void setId(int id)
	{
		this.id = id;
	}

	public String getProduct_id()
	{
		return product_id;
	}

	public void setProduct_id(String product_id)
	{
		this.product_id = product_id;
	}

	public String getHead_no()
	{
		return head_no;
	}

	public void setHead_no(String head_no)
	{
		this.head_no = head_no;
	}

	public double getDiameter()
	{
		return diameter;
	}

	public void setDiameter(double diameter)
	{
		this.diameter = diameter;
	}

	public int getTool_details_ref()
	{
		return tool_details_ref;
	}

	public void setTool_details_ref(int tool_details_ref)
	{
		this.tool_details_ref = tool_details_ref;
	}

	public String getTask()
	{
		return task;
	}

	public void setTask(String task)
	{
		this.task = task;
	}

	public String getIs_recommended()
	{
		return is_recommended;
	}

	public void setIs_recommended(String is_recommended)
	{
		this.is_recommended = is_recommended;
	}

	public String getModel()
	{
		return model;
	}

	public void setModel(String model)
	{
		this.model = model;
	}

	public String getModel_code()
	{
		return model_code;
	}

	public void setModel_code(String model_code)
	{
		this.model_code = model_code;
	}

	public String getPercentage_taper_from_tip()
	{
		return percentage_taper_from_tip;
	}

	public void setPercentage_taper_from_tip(String percentage_taper_from_tip)
	{
		this.percentage_taper_from_tip = percentage_taper_from_tip;
	}

	public int getTool_id()
	{
		return tool_id;
	}

	public void setTool_id(int tool_id)
	{
		this.tool_id = tool_id;
	}

	public int getTool_type_id()
	{
		return tool_type_id;
	}

	public void setTool_type_id(int tool_type_id)
	{
		this.tool_type_id = tool_type_id;
	}
}
