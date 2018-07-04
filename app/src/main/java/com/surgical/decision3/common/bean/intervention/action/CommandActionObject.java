package com.surgical.decision3.common.bean.intervention.action;

import org.simpleframework.xml.Root;

@Root
public class CommandActionObject extends BaseActionObject
{
	public CommandActionObject(Object location,
			Object detailsObject)
	{
		super( location, detailsObject);
//		this.location = location;
//		this.detailsObject = detailsObject;
	}
	public CommandActionObject()
	{
		super();
	}
	
	/*
	@Element
	Object location;
	
	@Element
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
	*/
}
