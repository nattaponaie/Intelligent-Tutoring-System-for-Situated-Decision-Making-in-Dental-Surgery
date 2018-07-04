package com.surgical.decision3.common.bean.tool;

import org.simpleframework.xml.Attribute;
import org.simpleframework.xml.Element;

public class BaseBur extends BaseTool
{
	public BaseBur()
	{
		super();
	}
	
	@Attribute
	String product_id;
	
	@Element
	String headNo;
	
	@Element
	double diameter;
	
	
	public String getProduct_id()
	{
		return product_id;
	}
	public void setProduct_id(String product_id)
	{
		this.product_id = product_id;
	}
	public String getHeadNo()
	{
		return headNo;
	}
	public void setHeadNo(String headNo)
	{
		this.headNo = headNo;
	}
	public double getDiameter()
	{
		return diameter;
	}
	public void setDiameter(double d)
	{
		this.diameter = d;
	}
	
	
}
