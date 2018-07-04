package com.surgical.decision3.common.bean.intervention.prompt;

import org.simpleframework.xml.Attribute;
import org.simpleframework.xml.Root;

@Root
public class PromptImage
{

	public PromptImage(String url)
	{
		super();
		this.url = url;
	}

	public PromptImage()
	{
		super();
	}

	@Attribute (required=false)
	String url;

	public String getUrl()
	{
		return url;
	}

	public void setUrl(String url)
	{
		this.url = url;
	}
	
	
}
