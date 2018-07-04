package com.surgical.decision3.common.bean.intraoperative;

import com.surgical.decision3.common.bean.tool.SelectedToolTray;
import com.surgical.decision3.common.bean.procedure.PatientCase;
import com.surgical.decision3.common.bean.procedure.Procedure;
import com.surgical.decision3.common.bean.procedure.Domain;
import com.surgical.decision3.common.bean.procedure.SubDomain;
import com.surgical.decision3.common.bean.procedure.SubProcedure;
import com.surgical.decision3.common.bean.procedure.SubProcedureStep;
import com.surgical.decision3.common.bean.scene.Scene;

import java.util.ArrayList;

import org.simpleframework.xml.Element;
import org.simpleframework.xml.ElementList;
import org.simpleframework.xml.Root;

import com.surgical.decision3.common.bean.tool.Set;
import com.surgical.decision3.common.bean.tool.Tool;

@Root
public class IntraOperativeCollection
{
	
	@Element(required = false)
	Scene scene;

	@Element(required = false)
	Domain domain;
	
	@Element(required = false)
	SubDomain subDomain;

	@Element(required = false)
	Procedure procedure;

	@Element(required = false)
	SubProcedure subProcedure;

	@Element(required = false, name = "case")
	PatientCase patientCase;

	@ElementList(required = false)
	ArrayList<SubProcedureStep> plans;
	
	@ElementList(required = false)
	ArrayList<Tool> selectedTools;
	
	@ElementList(required = false)
	ArrayList<Set> availableSets;

//	@ElementList(required = false)
//	ArrayList<SelectedTool> selectedTools;
	
	public ArrayList<Tool> getSelectedTools()
	{
		return selectedTools;
	}

	public void setSelectedTools(ArrayList<Tool> selectedTools)
	{
		this.selectedTools = selectedTools;
	}

	@Element (required = false)
    SelectedToolTray selectedToolTray;
	
	public SelectedToolTray getSelectedToolTray()
	{
		return selectedToolTray;
	}

	public void setSelectedToolTray(SelectedToolTray selectedToolTray)
	{
		this.selectedToolTray = selectedToolTray;
	}

	public IntraOperativeCollection()
	{
		super();
	}

	public Scene getScene()
	{
		return scene;
	}

	public void setScene(Scene scene)
	{
		this.scene = scene;
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

	public SubProcedure getSubProcedure()
	{
		return subProcedure;
	}

	public void setSubProcedure(SubProcedure subProcedure)
	{
		this.subProcedure = subProcedure;
	}

	public PatientCase getPatientCase()
	{
		return patientCase;
	}

	public void setPatientCase(PatientCase patientCase)
	{
		this.patientCase = patientCase;
	}

	public ArrayList<SubProcedureStep> getPlans()
	{
		return plans;
	}

	public void setPlans(ArrayList<SubProcedureStep> plans)
	{
		this.plans = plans;
	}

	public SubDomain getSubDomain()
	{
		return subDomain;
	}

	public void setSubDomain(SubDomain subDomain)
	{
		this.subDomain = subDomain;
	}

	public ArrayList<Set> getAvailableSets()
	{
		return availableSets;
	}

	public void setAvailableSets(ArrayList<Set> availableSets)
	{
		this.availableSets = availableSets;
	}
	
//	public ArrayList<SelectedTool> getSelectedTools()
//	{
//		return selectedTools;
//	}
//
//	public void setSelectedTools(ArrayList<SelectedTool> selectedTools)
//	{
//		this.selectedTools = selectedTools;
//	}
}
