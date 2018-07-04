package com.surgical.decision3.common.bean.tooth.rootcanal;

import org.simpleframework.xml.Attribute;
import org.simpleframework.xml.Element;
import org.simpleframework.xml.Root;

@Root (name="root")
public class RootCanal
{
	public RootCanal()
	{
		super();
	}
	
	public RootCanal(int no, int length, RootCanalFirstThird firstThird,
			RootCanalSecondThird secondThird)
	{
		super();
		this.no = no;
		this.length = length;
		this.firstThird = firstThird;
		this.secondThird = secondThird;
	}
	/*
	 		Integer index_id = allrows.getColumnIndex( "id" );
		Integer index_wt_id = allrows.getColumnIndex( "working_tooth_id" );
		Integer index_root_no = allrows.getColumnIndex( "root_no" );
		Integer index_root_length = allrows.getColumnIndex( "root_length" );
		Integer index_is_curved = allrows.getColumnIndex( "is_curved" );
		
	 * */
	
	private int id;
	private int working_tooth_id;
	private boolean isCurved;

	@Attribute
	private int no;			//root_no
	
	@Attribute
	private int length;		//root_length
	
	@Element (required=false)
	private RootCanalFirstThird firstThird;
	
	@Element (required=false)
	private RootCanalSecondThird secondThird;

	public int getNo()
	{
		return no;
	}

	public void setNo(int no)
	{
		this.no = no;
	}

	public int getLength()
	{
		return length;
	}

	public void setLength(int length)
	{
		this.length = length;
	}

	public RootCanalFirstThird getFirstThird()
	{
		return firstThird;
	}

	public void setFirstThird(RootCanalFirstThird firstThird)
	{
		this.firstThird = firstThird;
	}

	public RootCanalSecondThird getSecondThird()
	{
		return secondThird;
	}

	public void setSecondThird(RootCanalSecondThird secondThird)
	{
		this.secondThird = secondThird;
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

	public boolean isCurved()
	{
		return isCurved;
	}

	public void setCurved(boolean isCurved)
	{
		this.isCurved = isCurved;
	}
}
