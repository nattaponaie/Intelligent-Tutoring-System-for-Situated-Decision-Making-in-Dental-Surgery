package com.surgical.decision3.common.bean.tooth.rootcanal;

import org.simpleframework.xml.Attribute;
import org.simpleframework.xml.Root;

@Root ( name = "secondThird")
public class RootCanalSecondThird
{
	public RootCanalSecondThird()
	{
		super();
	}
	
	public RootCanalSecondThird(boolean isCurved)
	{
		super();
		this.isCurved = isCurved;
	}

	private int id;
	private int working_tooth_id;
	private int root_id;
	
	@Attribute (required=false)
	private double length;

	@Attribute
	private boolean isCurved;

	public boolean isCurved()
	{
		return isCurved;
	}

	public void setCurved(boolean isCurved)
	{
		this.isCurved = isCurved;
	}

	public double getLength()
	{
		return length;
	}

	public void setLength(double length)
	{
		this.length = length;
	}

	public int getId()
	{
		return id;
	}

	public void setId(int id)
	{
		this.id = id;
	}

	public int getWorking_tooth_id()
	{
		return working_tooth_id;
	}

	public void setWorking_tooth_id(int working_tooth_id)
	{
		this.working_tooth_id = working_tooth_id;
	}

	public int getRoot_id()
	{
		return root_id;
	}

	public void setRoot_id(int root_id)
	{
		this.root_id = root_id;
	}
	
	
}
