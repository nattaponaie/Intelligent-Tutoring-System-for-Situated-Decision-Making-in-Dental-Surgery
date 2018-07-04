package com.surgical.decision3.common.bean.disease;

import org.simpleframework.xml.Attribute;
import org.simpleframework.xml.Root;

@Root
public class CongenitalDisease
{
	@Attribute(required = false)
	int id;

	@Attribute(required = false)
	String name;

	@Attribute(required = false)
	String description;

	public CongenitalDisease()
	{
		super();
	}

	public CongenitalDisease(int id, String name, String description)
	{
		this.id = id;
		this.name = name;
		this.description = description;
	}

	public int getId()
	{
		return id;
	}

	public void setId(int id)
	{
		this.id = id;
	}

	public String getName()
	{
		return name;
	}

	public void setName(String name)
	{
		this.name = name;
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
