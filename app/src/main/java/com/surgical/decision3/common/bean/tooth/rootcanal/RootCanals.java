package com.surgical.decision3.common.bean.tooth.rootcanal;

import java.util.ArrayList;



//@Root 
public class RootCanals
{
	//@ElementList (name="roots")
	private ArrayList <RootCanal> rootCanals;
	
	//@Attribute
	private int totalRoots;
	
	public void setTotalRoots(int totalRoots)
	{
		this.totalRoots = totalRoots;
	}

	public RootCanals()
	{
		super();
		this.rootCanals = new ArrayList <RootCanal>();
	}

	public void addRootCanal( RootCanal rc )
	{
		this.rootCanals.add( rc );
		this.totalRoots = this.rootCanals.size();
	}
	
	public ArrayList <RootCanal> getRootCanals()
	{
		return rootCanals;
	}

	public void setRootCanals(ArrayList <RootCanal> rootCanals)
	{
		this.rootCanals = rootCanals;
	}

	public int getTotalRoots()
	{
//		if( rootCanals != null )
//		{
//			totalRoots = rootCanals.size();
//		}
//		else
//		{
//			totalRoots = 0;
//		}
		
		return totalRoots = rootCanals.size();
	}

}
