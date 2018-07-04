package com.surgical.decision3.common.bean.intervention.prompt;

import org.simpleframework.xml.Attribute;
import org.simpleframework.xml.Root;

@Root
public class PromptMsgExplanation
{
	public PromptMsgExplanation(String text)
	{
		super();
		this.text = text;
	}

	public PromptMsgExplanation()
	{
		super();
	}
	
	@Attribute
	String text;

	public String getText()
	{
		return text;
	}

	public void setText(String text)
	{
		this.text = text;
	}
}
