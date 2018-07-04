package com.surgical.decision3.common.bean.preoperative;

import com.surgical.decision3.common.bean.procedure.PatientCase;
import com.surgical.decision3.common.bean.procedure.Procedure;
import com.surgical.decision3.common.bean.procedure.Domain;
import com.surgical.decision3.common.bean.procedure.SubProcedure;

import java.util.ArrayList;

import org.simpleframework.xml.Attribute;
import org.simpleframework.xml.Element;
import org.simpleframework.xml.ElementList;

import com.surgical.decision3.common.bean.procedure.SubDomain;
import com.surgical.decision3.common.bean.tool.Tool;

//Class that store communicating data in Preoperative
public class PreoperativeDataStream
{
	@Attribute (required=false)
	String action;
	
	//Login
	@Element (required=false)
	Login	login;
	
	//Registration
	@Element (required=false)
	Registration registration;
	
	//SelectedDomain
	@Element (required=false)
	Domain selectedDomain;
	
	//SelectedSubDomain
	@Element (required=false)
	SubDomain selectedSubDomain;
	
	//SelectedProcedure
	@Element (required=false)
	Procedure selectedProcedure;
	
	@Element (required=false)
	SubProcedure selectedSubprocedure;
	
	//SelectedPatientCase			//Ref: PatientCase
	@Element (required=false)
	PatientCase selectedPatientCase;
	
	@ElementList(required=false)
	ArrayList<SubProcedure> subProcedures;		//for plan submisstion
	
	
	
	public SubProcedure getSelectedSubprocedure()
	{
		return selectedSubprocedure;
	}

	public void setSelectedSubprocedure(SubProcedure selectedSubprocedure)
	{
		this.selectedSubprocedure = selectedSubprocedure;
	}

	public ArrayList<SubProcedure> getSubProcedures()
	{
		return subProcedures;
	}

	public void setSubProcedures(ArrayList<SubProcedure> subProcedures)
	{
		this.subProcedures = subProcedures;
	}

	public String getAction()
	{
		return action;
	}

	public void setAction(String action)
	{
		this.action = action;
	}

	public Login getLogin()
	{
		return login;
	}

	public void setLogin(Login login)
	{
		this.login = login;
	}

	public Registration getRegistration()
	{
		return registration;
	}

	public void setRegistration(Registration registration)
	{
		this.registration = registration;
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

	public PatientCase getSelectedPatientCase()
	{
		return selectedPatientCase;
	}

	public void setSelectedPatientCase(PatientCase selectedPatientCase)
	{
		this.selectedPatientCase = selectedPatientCase;
	}
	
	@ElementList(required=false)
	ArrayList<Tool> selectedTools;

	public ArrayList<Tool> getSelectedTools()
	{
		return selectedTools;
	}

	public void setSelectedTools(ArrayList<Tool> selectedTools)
	{
		this.selectedTools = selectedTools;
	}
	
}
