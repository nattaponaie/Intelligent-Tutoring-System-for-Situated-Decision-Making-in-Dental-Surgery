package com.surgical.decision3.common.bean.tooth;

import org.simpleframework.xml.Attribute;
import org.simpleframework.xml.Root;

@Root
public class Dentine
{
	public Dentine()
	{
		super();
	}
	
	public Dentine(double thickness)
	{
		super();
		this.thickness = thickness;
	}

	@Attribute
	private double thickness;
	
	@Attribute (required=false)
	private boolean isDrilled = false; 

	public double getThickness()
	{
		return thickness;
	}

	public void setThickness(double thickness)
	{
		this.thickness = thickness;
	}

	public boolean isDrilled()
	{
		return isDrilled;
	}

	public void setDrilled(boolean isDrill)
	{
		this.isDrilled = isDrill;
	}
}
