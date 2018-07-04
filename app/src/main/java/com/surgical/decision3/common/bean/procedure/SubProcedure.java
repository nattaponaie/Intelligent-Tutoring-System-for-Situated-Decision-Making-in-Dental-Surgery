package com.surgical.decision3.common.bean.procedure;

import org.simpleframework.xml.Attribute;
import org.simpleframework.xml.Root;

@Root
public class SubProcedure extends BaseProcedure
{
	public SubProcedure()
	{
		super();
	}

	public SubProcedure(int id, String name, String code, String desc, int procId)
	{
		super(id, name, code, desc);
		this.procedureId = procId;
	}

	@Attribute(required=false)
	private int procedureId;
	
	public int getProcedureId()
	{
		return procedureId;
	}

	public void setProcedureId(int procedureId)
	{
		this.procedureId = procedureId;
	}
}
