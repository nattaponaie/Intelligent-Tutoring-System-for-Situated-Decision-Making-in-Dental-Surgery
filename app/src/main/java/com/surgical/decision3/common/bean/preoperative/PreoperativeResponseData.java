package com.surgical.decision3.common.bean.preoperative;

import com.surgical.decision3.common.bean.procedure.Domain;
import com.surgical.decision3.common.bean.procedure.PatientCase;
import com.surgical.decision3.common.bean.procedure.Procedure;
import com.surgical.decision3.common.bean.procedure.SubDomain;
import com.surgical.decision3.common.bean.procedure.SubProcedure;

import java.util.ArrayList;

import org.simpleframework.xml.Element;
import org.simpleframework.xml.ElementList;

import com.surgical.decision3.common.bean.tool.Tool;

public class PreoperativeResponseData
{
	@ElementList(required=false)
	ArrayList<Domain> domains;
	
	@ElementList(required=false)
	ArrayList<SubDomain> subdomains;
	
	@ElementList(required=false)
	ArrayList<Procedure> procedures;
	
	@ElementList(required=false)
	ArrayList<SubProcedure> subProcedures;	//Send subprocedure just for data. but it's not binded with patient case.
	
	@ElementList(required=false)
	ArrayList<PatientCase> patientCases;	//Bind with Procedure id,

	@Element(required=false)
	PatientCase selectedPatientCase;
	
	@ElementList(required=false)
	ArrayList<Tool> tools;
	
	@Element(required=false)
	Domain selectedDomain;
	
	@Element(required=false)
	SubDomain selectedSubDomain;
	
	@Element(required=false)
	Procedure selectedProcedure;
	
	public ArrayList<Tool> getTools()
	{
		return tools;
	}

	public void setTools(ArrayList<Tool> tools)
	{
		this.tools = tools;
	}

	public PatientCase getSelectedPatientCase()
	{
		return selectedPatientCase;
	}

	public void setSelectedPatientCase(PatientCase selectedPatientCase)
	{
		this.selectedPatientCase = selectedPatientCase;
	}
	
	public ArrayList<Domain> getDomains()
	{
		return domains;
	}

	public void setDomains(ArrayList<Domain> domains)
	{
		this.domains = domains;
	}

	public ArrayList<SubDomain> getSubdomains()
	{
		return subdomains;
	}

	public void setSubdomains(ArrayList<SubDomain> subdomains)
	{
		this.subdomains = subdomains;
	}

	public ArrayList<Procedure> getProcedures()
	{
		return procedures;
	}

	public void setProcedures(ArrayList<Procedure> procedures)
	{
		this.procedures = procedures;
	}

	public ArrayList<SubProcedure> getSubProcedures()
	{
		return subProcedures;
	}

	public void setSubProcedures(ArrayList<SubProcedure> subProcedures)
	{
		this.subProcedures = subProcedures;
	}

	public ArrayList<PatientCase> getPatientCases()
	{
		return patientCases;
	}

	public void setPatientCases(ArrayList<PatientCase> patientCases)
	{
		this.patientCases = patientCases;
	}
	
	public Domain getSelectedDomain()
	{
		return selectedDomain;
	}

	public void setSelectedDomain(Domain selectedDomain)
	{
		this.selectedDomain = selectedDomain;
	}

	public SubDomain getSelectedSubDomain()
	{
		return selectedSubDomain;
	}

	public void setSelectedSubDomain(SubDomain selectedSubDomain)
	{
		this.selectedSubDomain = selectedSubDomain;
	}

	public Procedure getSelectedProcedure()
	{
		return selectedProcedure;
	}

	public void setSelectedProcedure(Procedure selectedProcedure)
	{
		this.selectedProcedure = selectedProcedure;
	}
}
