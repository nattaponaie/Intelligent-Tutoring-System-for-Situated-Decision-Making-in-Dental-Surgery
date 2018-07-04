package com.surgical.decision3.common.bean.tooth.rootcanal;

import org.simpleframework.xml.Attribute;
import org.simpleframework.xml.Root;

@Root ( name = "firstThird")
public class RootCanalFirstThird
{
	public RootCanalFirstThird()
	{
		super();
	}
	
	public RootCanalFirstThird(boolean isCurved)
	{
		super();
		this.isCurved = isCurved;
	}
	
/*
 *	 	public static final String CREATE_TABLE_ROOT_CANAL_FIRST_THIRD = "CREATE TABLE IF NOT EXISTS `root_canal_first_third` ( "
			+ " `id` INTEGER PRIMARY KEY AUTOINCREMENT, "
			+ " `working_tooth_id` INTEGER NOT NULL, "
			+ " `root_id` INTEGER NOT NULL, "
			+ " `length` REAL, "
			+ " `is_curved` TEXT NOT NULL ); ";
 * */
	
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
