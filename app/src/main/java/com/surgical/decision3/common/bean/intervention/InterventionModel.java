package com.surgical.decision3.common.bean.intervention;

import org.simpleframework.xml.Attribute;
import org.simpleframework.xml.Root;

@Root
public class InterventionModel
{

	public InterventionModel(String name, String code, String description)
	{
		super();
		this.name = name;
		this.code = code;
		this.description = description;
	}

	public InterventionModel()
	{
		super();
	}

	@Attribute (required=false)
	String name;

	@Attribute(required=false)
	String code;

	@Attribute(required=false)
	String description;

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

	public String getDescription()
	{
		return description;
	}

	public void setDescription(String description)
	{
		this.description = description;
	}

}
