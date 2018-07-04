package com.surgical.decision3.common.bean.intervention;

import org.simpleframework.xml.Attribute;
import org.simpleframework.xml.Root;

@Root
public class InterventionType
{
	public InterventionType()
	{
		super();
	}

	public InterventionType(String name, String code)
	{
		super();
		this.name = name;
		this.code = code;
	}

	@Attribute (required=false)
	String name;

	@Attribute(required=false)
	String code;

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

}
