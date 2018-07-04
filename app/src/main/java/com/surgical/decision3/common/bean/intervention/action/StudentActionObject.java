package com.surgical.decision3.common.bean.intervention.action;

import org.simpleframework.xml.Root;

@Root (name="studentAction")
public class StudentActionObject extends BaseActionObject
{
	public StudentActionObject(Object location,
			Object detailsObject)
	{
		super( location, detailsObject);
	}
	public StudentActionObject()
	{
		super();
	}
}
