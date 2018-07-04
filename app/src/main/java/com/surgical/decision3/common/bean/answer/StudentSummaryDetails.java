package com.surgical.decision3.common.bean.answer;

import com.surgical.decision3.common.bean.procedure.SubProcedure;
import com.surgical.decision3.common.bean.procedure.SubProcedureStep;

import org.simpleframework.xml.Element;

public class StudentSummaryDetails
{
	@Element(required = false)
	StudentPerformance performance;
	
	@Element(required = false)
	StudentAction2 studentAction;
	
	@Element(required = false)
	SubProcedure subprocedure;
	
	@Element(required = false)
	SubProcedureStep subProcedureStep;

	public StudentPerformance getPerformance()
	{
		return performance;
	}

	public void setPerformance(StudentPerformance performance)
	{
		this.performance = performance;
	}

	public StudentAction2 getStudentAction()
	{
		return studentAction;
	}

	public void setStudentAction(StudentAction2 studentAction)
	{
		this.studentAction = studentAction;
	}

	public SubProcedure getSubprocedure()
	{
		return subprocedure;
	}

	public void setSubprocedure(SubProcedure subprocedure)
	{
		this.subprocedure = subprocedure;
	}

	public SubProcedureStep getSubProcedureStep()
	{
		return subProcedureStep;
	}

	public void setSubProcedureStep(SubProcedureStep subProcedureStep)
	{
		this.subProcedureStep = subProcedureStep;
	}
}
