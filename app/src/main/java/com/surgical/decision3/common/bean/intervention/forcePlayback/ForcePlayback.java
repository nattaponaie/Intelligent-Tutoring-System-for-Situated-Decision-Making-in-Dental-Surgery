package com.surgical.decision3.common.bean.intervention.forcePlayback;

import com.surgical.decision3.common.bean.intervention.action.CommandActionStep;
import com.surgical.decision3.common.bean.procedure.Domain;
import com.surgical.decision3.common.bean.procedure.Procedure;
import com.surgical.decision3.common.bean.procedure.SubDomain;
import com.surgical.decision3.common.bean.procedure.SubProcedure;
import com.surgical.decision3.common.bean.procedure.SubProcedureStep;

import java.util.ArrayList;

import org.simpleframework.xml.Element;
import org.simpleframework.xml.ElementList;
import org.simpleframework.xml.Root;

@Root
public class ForcePlayback
{
	@Element (required=false)
	Domain domain;
	
	@Element (required=false)
	SubDomain subDomain;

	@Element (required=false)
	Procedure procedure;

	@Element (required=false)
	SubProcedure subprocedure;

	@Element (required=false)
	SubProcedureStep subprocedureStep;

	@ElementList (required=false)
	ArrayList <CommandActionStep> actionSteps;
	
	public ForcePlayback()
	{
		//actionSteps = new ArrayList <CommandActionStep>();
	}
	
	public SubDomain getSubDomain()
	{
		return subDomain;
	}


	public void setSubDomain(SubDomain subDomain)
	{
		this.subDomain = subDomain;
	}


	public Domain getDomain()
	{
		return domain;
	}

	public void setDomain(Domain domain)
	{
		this.domain = domain;
	}

	public Procedure getProcedure()
	{
		return procedure;
	}

	public void setProcedure(Procedure procedure)
	{
		this.procedure = procedure;
	}

	public SubProcedure getSubprocedure()
	{
		return subprocedure;
	}

	public void setSubprocedure(SubProcedure subprocedure)
	{
		this.subprocedure = subprocedure;
	}

	public SubProcedureStep getSubprocedureStep()
	{
		return subprocedureStep;
	}

	public void setSubprocedureStep(SubProcedureStep subprocedureStep)
	{
		this.subprocedureStep = subprocedureStep;
	}

//	public ArrayList <commandac> getActions()
//	{
//		return actions;
//	}
//
//	public void setActions(ArrayList <CommandAction> actions)
//	{
//		this.actions = actions;
//	}

	public void addCommandActionStep( CommandActionStep actionStep )
	{
		if( actionSteps == null )
		{
			actionSteps = new ArrayList <CommandActionStep>();
		}
		
		this.actionSteps.add( actionStep );
	}

	public ArrayList <CommandActionStep> getActionSteps()
	{
		return actionSteps;
	}

	public void setActionSteps(ArrayList <CommandActionStep> actionSteps)
	{
		this.actionSteps = actionSteps;
	}
	

	
}
