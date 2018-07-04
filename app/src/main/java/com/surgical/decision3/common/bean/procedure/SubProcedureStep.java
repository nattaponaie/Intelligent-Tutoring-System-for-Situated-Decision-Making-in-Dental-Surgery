package com.surgical.decision3.common.bean.procedure;

import org.simpleframework.xml.Attribute;
import org.simpleframework.xml.Root;

@Root
public class SubProcedureStep
{

	public SubProcedureStep()
	{
		super();
	}

	public SubProcedureStep(int id, String name, String code,
			String description, int subprocedureId)
	{
		super();
		this.id = id;
		this.name = name;
		this.code = code;
		this.description = description;
		this.subprocedureId = subprocedureId;
	}

	@Attribute
	private int id;

	@Attribute
	private String name;

	@Attribute
	private String code;

	@Attribute
	private String description;

	@Attribute
	private int subprocedureId;

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

	public int getSubprocedureId()
	{
		return subprocedureId;
	}

	public void setSubprocedureId(int subprocedureId)
	{
		this.subprocedureId = subprocedureId;
	}
}
