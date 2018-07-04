package com.surgical.decision3.common.bean.tooth;

import org.simpleframework.xml.Attribute;
import org.simpleframework.xml.Root;

@Root
public class PulpChamber
{
	public boolean isDentineShelfRemove()
	{
		return isDentineShelfRemove;
	}

	public void setDentineShelfRemove(boolean isDentineShelfRemove)
	{
		this.isDentineShelfRemove = isDentineShelfRemove;
	}

	public PulpChamber()
	{
		super();
	}
	
	public PulpChamber(double height, double width)
	{
		super();
		this.height = height;
		this.width = width;
	}

	@Attribute
	private double height;
	
	@Attribute
	private double width;
	
	@Attribute (required=false)
	private boolean isDentineShelfRemove= false;

	public double getHeight()
	{
		return height;
	}

	public void setHeight(double height)
	{
		this.height = height;
	}

	public double getWidth()
	{
		return width;
	}

	public void setWidth(double weight)
	{
		this.width = weight;
	}

}
