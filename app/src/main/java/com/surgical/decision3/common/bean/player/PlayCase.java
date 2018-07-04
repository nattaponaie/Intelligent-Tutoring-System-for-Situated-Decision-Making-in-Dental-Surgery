package com.surgical.decision3.common.bean.player;

import org.simpleframework.xml.Attribute;
import org.simpleframework.xml.Root;

@Root 
public class PlayCase
{
	@Attribute(required=false)
	int id;
	
	@Attribute(required=false)
	int case_id;

	public int getId()
	{
		return id;
	}

	public void setId(int id)
	{
		this.id = id;
	}

	public int getCase_id()
	{
		return case_id;
	}

	public void setCase_id(int case_id)
	{
		this.case_id = case_id;
	}
}
