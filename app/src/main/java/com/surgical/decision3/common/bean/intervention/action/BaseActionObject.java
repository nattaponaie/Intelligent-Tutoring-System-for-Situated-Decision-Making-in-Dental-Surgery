package com.surgical.decision3.common.bean.intervention.action;

import org.simpleframework.xml.Element;

public class BaseActionObject
{
	public BaseActionObject(Object location,
			Object detailsObject)
	{
		super();
		this.location = location;
		this.detailsObject = detailsObject;
	}
	
	public BaseActionObject()
	{
		super();
	}
	
	@Element (required=false)
	Object location;
	
	@Element (required=false)
	Object detailsObject;   //[OBJECT CLASS]
	
	public Object getLocation()
	{
		return location;
	}
	
	public void setLocation(Object location)
	{
		this.location = location;
	}
	
	public Object getDetailsObject()
	{
		return detailsObject;
	}
	
	public void setDetailsObject(Object detailsObject)
	{
		this.detailsObject = detailsObject;
	}
}
