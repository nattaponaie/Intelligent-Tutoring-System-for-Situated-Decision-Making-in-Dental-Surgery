package com.surgical.decision3.common.bean.procedure;

import org.simpleframework.xml.Attribute;
import org.simpleframework.xml.Root;

@Root
public class Procedure extends BaseProcedure
{
	public Procedure()
	{
		super();
	}

	public Procedure(int id, String name, String code, String desc, int subDomainId )
	{
		super( id, name, code, desc );
//		this.domainId = domainId;
		this.subDomainId = subDomainId;
	}

//	@Attribute 
//	private int domainId;
//
//	public int getDomainId()
//	{
//		return domainId;
//	}
//
//	public void setDomainId(int domainId)
//	{
//		this.domainId = domainId;
//	}
	
	//NOTE: procedure does not tie with domain, but subdomain. This might effect initial intraoperative

	@Attribute  (required=false)
	private int subDomainId;

	public int getSubDomainId()
	{
		return subDomainId;
	}

	public void setSubDomainId(int subDomainId)
	{
		this.subDomainId = subDomainId;
	}

}
