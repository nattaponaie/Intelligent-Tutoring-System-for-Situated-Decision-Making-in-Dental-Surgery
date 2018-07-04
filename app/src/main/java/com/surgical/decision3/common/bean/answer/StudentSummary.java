package com.surgical.decision3.common.bean.answer;

import com.surgical.decision3.common.bean.procedure.Domain;
import com.surgical.decision3.common.bean.procedure.PatientCase;
import com.surgical.decision3.common.bean.procedure.Procedure;
import com.surgical.decision3.common.bean.procedure.SubDomain;

import java.util.ArrayList;

import org.simpleframework.xml.Element;
import org.simpleframework.xml.ElementList;
import org.simpleframework.xml.Root;

/**
 * Summary of student performance for UNITY
 * */
@Root
public class StudentSummary
{
	//something quite similar to intracollection
	
	@Element(required = false)
	Domain domain;
	
	@Element(required = false)
	SubDomain subDomain;

	@Element(required = false)
	Procedure procedure;

	@Element(required = false, name = "case")
	PatientCase patientCase;
	
	@ElementList(required = false)
	ArrayList<StudentSummaryDetails> summaryDetails;

	public Domain getDomain()
	{
		return domain;
	}

	public void setDomain(Domain domain)
	{
		this.domain = domain;
	}

	public SubDomain getSubDomain()
	{
		return subDomain;
	}

	public void setSubDomain(SubDomain subDomain)
	{
		this.subDomain = subDomain;
	}

	public Procedure getProcedure()
	{
		return procedure;
	}

	public void setProcedure(Procedure procedure)
	{
		this.procedure = procedure;
	}

	public PatientCase getPatientCase()
	{
		return patientCase;
	}

	public void setPatientCase(PatientCase patientCase)
	{
		this.patientCase = patientCase;
	}

	public ArrayList<StudentSummaryDetails> getSummaryDetails()
	{
		return summaryDetails;
	}

	public void setSummaryDetails(ArrayList<StudentSummaryDetails> summaryDetails)
	{
		this.summaryDetails = summaryDetails;
	}
}
