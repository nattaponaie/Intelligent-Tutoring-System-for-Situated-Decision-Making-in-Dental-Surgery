package com.surgical.decision3.common.bean.tool;

import org.simpleframework.xml.Element;
import org.simpleframework.xml.Root;

@Root
public class SteelRoundBur extends BaseBur
{
	public SteelRoundBur()
	{
		super();
	}

	@Element
	int size;
	
	@Element
	int shankLength;

	public int getSize()
	{
		return size;
	}

	public void setSize(int size)
	{
		this.size = size;
	}

	public int getShankLength()
	{
		return shankLength;
	}

	public void setShankLength(int shankLength)
	{
		this.shankLength = shankLength;
	}
	
	
}
