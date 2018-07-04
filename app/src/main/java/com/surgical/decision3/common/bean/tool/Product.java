package com.surgical.decision3.common.bean.tool;

import org.simpleframework.xml.Root;
import org.simpleframework.xml.Text;

@Root
public class Product
{
	public Product()
	{
		super();
	}
	
	@Text
	String productName;

	public String getProductName()
	{
		return productName;
	}

	public void setProductName(String productName)
	{
		this.productName = productName;
	}
}
