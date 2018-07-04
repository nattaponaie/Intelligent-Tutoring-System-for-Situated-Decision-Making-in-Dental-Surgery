package com.surgical.decision3.common.bean.intervention.action;

import org.simpleframework.xml.Root;

@Root
public class InterventionStudentAction extends BaseAction
{
	public InterventionStudentAction()
	{
		super();
	}
	
	public InterventionStudentAction(String actionDescription, String actionCode,
                                     String semanticActionCode, StudentActionObject onObject,
                                     StudentActionObject toObject)
	{
		super( actionDescription, actionCode, semanticActionCode, onObject,
				toObject );
	}
}
