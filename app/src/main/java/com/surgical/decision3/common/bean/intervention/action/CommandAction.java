package com.surgical.decision3.common.bean.intervention.action;

import org.simpleframework.xml.Root;

@Root
public class CommandAction extends BaseAction
{
	public CommandAction()
	{
		super();
	}

	public CommandAction(String actionDescription, String actionCode,
			String semanticActionCode, CommandActionObject onObject,
			CommandActionObject toObject)
	{
		super( actionDescription, actionCode, semanticActionCode, onObject,
				toObject );
	}

}
