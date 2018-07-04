package com.surgical.decision3.common.bean.intervention.prompt;

import org.simpleframework.xml.Attribute;
import org.simpleframework.xml.Element;
import org.simpleframework.xml.Root;

@Root
public class Prompt
{
	public Prompt(String type, boolean hasPromptImage, PromptImage promptImage,
			PromptMsg promptMsg, PromptMsgExplanation promptMsgExplanation)
	{
		super();
		this.type = type;
		this.hasPromptImage = hasPromptImage;
		this.promptImage = promptImage;
		this.promptMsg = promptMsg;
		this.promptMsgExplanation = promptMsgExplanation;
	}

	public Prompt()
	{
		super();
	}
	
	@Attribute(name = "error_code", required=false)
	String error_code;
	
	public String getError_code()
	{
		return error_code;
	}

	public void setError_code(String error_code)
	{
		this.error_code = error_code;
	}

	@Attribute(name = "promptType")
	String type;

	@Attribute (required = false)
	boolean hasPromptImage;

	@Element (required = false)
	PromptImage promptImage;

	@Element (required = false)
	PromptMsg promptMsg;

	@Element (required = false)
	PromptMsgExplanation promptMsgExplanation;

	public String getType()
	{
		return type;
	}

	public void setType(String type)
	{
		this.type = type;
	}

	public boolean isHasPromptImage()
	{
		return hasPromptImage;
	}

	public void setHasPromptImage(boolean hasPromptImage)
	{
		this.hasPromptImage = hasPromptImage;
	}

	public PromptImage getPromptImage()
	{
		return promptImage;
	}

	public void setPromptImage(PromptImage promptImage)
	{
		this.promptImage = promptImage;
	}

	public PromptMsg getPromptMsg()
	{
		return promptMsg;
	}

	public void setPromptMsg(PromptMsg promptMsg)
	{
		this.promptMsg = promptMsg;
	}

	public PromptMsgExplanation getPromptMsgExplanation()
	{
		return promptMsgExplanation;
	}

	public void setPromptMsgExplanation(
			PromptMsgExplanation promptMsgExplanation)
	{
		this.promptMsgExplanation = promptMsgExplanation;
	}

}
