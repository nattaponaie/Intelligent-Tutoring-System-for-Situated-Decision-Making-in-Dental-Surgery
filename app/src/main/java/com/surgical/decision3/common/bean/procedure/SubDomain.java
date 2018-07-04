package com.surgical.decision3.common.bean.procedure;

import org.simpleframework.xml.Attribute;

public class SubDomain
{
	@Attribute (required=false)
	private int domainId;
	
	@Attribute
	private int id;
	
	@Attribute (required=false)
	private String name;
	
	@Attribute (required=false)
	private String code;
	
	@Attribute (required=false)
	private String description;
	
	public SubDomain()
	{
		super();
	}

	public SubDomain(int id, String name, String code, String description, int domain_id)
	{
		super();
		this.id = id;
		this.name = name;
		this.code = code;
		this.description = description;
		this.domainId = domain_id;
	}
	
	public int getDomainId()
	{
		return domainId;
	}

	public void setDomainId(int domain_id)
	{
		this.domainId = domain_id;
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
