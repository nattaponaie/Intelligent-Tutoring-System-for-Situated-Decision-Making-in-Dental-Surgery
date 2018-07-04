package com.surgical.decision3.common.bean.tool;

import org.simpleframework.xml.Element;
import org.simpleframework.xml.Root;

@Root
public class RoundDiamondBur extends BaseBur
{
	//NOTE TO DEPRECATE

	public RoundDiamondBur()
	{
		super();
	}

	@Element
	DrillAction toolAction;

	public DrillAction getToolAction()
	{
		return toolAction;
	}

	public void setToolAction(DrillAction toolAction)
	{
		this.toolAction = toolAction;
	}
	
	
}
