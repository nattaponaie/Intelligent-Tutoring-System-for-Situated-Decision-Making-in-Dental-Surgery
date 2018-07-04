package com.surgical.decision3.common.bean.intervention.action;

import org.simpleframework.xml.Root;

@Root
public class StudentAction extends BaseAction
{
	public StudentAction()
	{
		super();
	}
	
	public StudentAction(String actionDescription, String actionCode,
			String semanticActionCode, StudentActionObject onObject,
			StudentActionObject toObject)
	{
		super( actionDescription, actionCode, semanticActionCode, onObject,
				toObject );
	}
}
