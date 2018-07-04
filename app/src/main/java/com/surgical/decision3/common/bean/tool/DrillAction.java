package com.surgical.decision3.common.bean.tool;

import org.simpleframework.xml.Attribute;
import org.simpleframework.xml.Root;

@Root
public class DrillAction 
{

	public DrillAction(String direction, double depth)
	{
		super();
		this.direction = direction;
		this.depth = depth;
	}

	public DrillAction()
	{
		super();
	}
	
	@Attribute
	String direction;
	
	@Attribute
	double depth;

	public String getDirection()
	{
		return direction;
	}

	public void setDirection(String direction)
	{
		this.direction = direction;
	}

	public double getDepth()
	{
		return depth;
	}

	public void setDepth(double depth)
	{
		this.depth = depth;
	}
}
