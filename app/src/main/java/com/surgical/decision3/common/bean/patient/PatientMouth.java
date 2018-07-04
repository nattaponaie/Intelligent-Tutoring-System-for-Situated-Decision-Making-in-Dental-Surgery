package com.surgical.decision3.common.bean.patient;

import com.surgical.decision3.common.bean.tooth.WorkingTooth;

import org.simpleframework.xml.Element;
import org.simpleframework.xml.Root;

@Root
public class PatientMouth
{
	public PatientMouth()
	{
		super();
		//wTeeth = new ArrayList <WorkingTooth>();
	}
	
	@Element (required=false)
	WorkingTooth workingTooth;

	public WorkingTooth getWorkingTooth()
	{
		return workingTooth;
	}

	public void setWorkingTooth(WorkingTooth workingTooth)
	{
		this.workingTooth = workingTooth;
	}
	
//	ArrayList <WorkingTooth> wTeeth;
//
//	public ArrayList <WorkingTooth> getwTeeth()
//	{
//		return wTeeth;
//	}
//
//	public void setwTeeth(ArrayList <WorkingTooth> wTeeth)
//	{
//		this.wTeeth = wTeeth;
//	}
	
	
	
}
