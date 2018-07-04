package com.surgical.decision3.common.bean.tool;

import org.simpleframework.xml.Element;
import org.simpleframework.xml.Root;

@Root
public class Clamp extends BaseTool
{
	String product_id;
	
	@Element
	String clampNo;

	public String getProduct_id()
	{
		return product_id;
	}

	public void setProduct_id(String product_id)
	{
		this.product_id = product_id;
	}

	public String getClampNo()
	{
		return clampNo;
	}

	public void setClampNo(String clampNo)
	{
		this.clampNo = clampNo;
	}
	
	
}
