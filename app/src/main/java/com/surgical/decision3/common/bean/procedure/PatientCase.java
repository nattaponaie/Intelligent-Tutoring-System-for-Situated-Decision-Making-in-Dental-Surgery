package com.surgical.decision3.common.bean.procedure;

import com.surgical.decision3.common.bean.radiograph.Radiograph;
import com.surgical.decision3.common.bean.patient.PatientHistory;
import com.surgical.decision3.common.bean.tooth.WorkingTooth;

import java.util.ArrayList;

import org.simpleframework.xml.Attribute;
import org.simpleframework.xml.Element;
import org.simpleframework.xml.ElementList;
import org.simpleframework.xml.Root;

import com.surgical.decision3.common.bean.disease.CongenitalDisease;
import com.surgical.decision3.common.bean.patient.Patient;

@Root(name = "case")
public class PatientCase
{
	public PatientCase()
	{
		super();
	}

	public PatientCase(int id, String name, String desc)
	{
		super();
		this.id = id;
		this.name = name;
		this.description = desc;

		this.radiographs = new ArrayList<Radiograph>();
		this.congenitalDiseases = new ArrayList<CongenitalDisease>();
		this.patientHistories = new ArrayList<PatientHistory>();
	}

	@Attribute
	private int id;

	@Attribute(required = false)
	private String name;

	@Attribute(required = false)
	private String description;

	@Element(required = false)
	private Patient patient;

	@ElementList(required = false)
	private ArrayList<Radiograph> radiographs;

	@Element(required = false)
	private WorkingTooth workingTooth;

	@ElementList(required = false)
	ArrayList<CongenitalDisease> congenitalDiseases;

	@ElementList(required = false)
	ArrayList<PatientHistory> patientHistories;

	@Attribute(required = false)
	private int patient_age;	//TO REMOVE: 
								// The age of patient in case is in patient object. 
								// Note: The age of patient in the case is static. It's not dynamic as patient's record.

	public int getPatient_age()
	{
		return patient_age;
	}

	public void setPatient_age(int patient_age)
	{
		this.patient_age = patient_age;
	}

	int procedure_id;

	public int getProcedure_id()
	{
		return procedure_id;
	}

	public void setProcedure_id(int procedure_id)
	{
		this.procedure_id = procedure_id;
	}

	public ArrayList<CongenitalDisease> getCongenitalDiseases()
	{
		return congenitalDiseases;
	}

	public void setCongenitalDiseases(
			ArrayList<CongenitalDisease> congenitalDiseases)
	{
		this.congenitalDiseases = congenitalDiseases;
	}

	public ArrayList<PatientHistory> getPatientHistories()
	{
		return patientHistories;
	}

	public void setPatientHistories(ArrayList<PatientHistory> patientHistories)
	{
		this.patientHistories = patientHistories;
	}

	public int getId()
	{
		return id;
	}

	public void setId(int id)
	{
		this.id = id;
	}

	public String getName()
	{
		return name;
	}

	public void setName(String name)
	{
		this.name = name;
	}

	public String getDescription()
	{
		return description;
	}

	public void setDescription(String description)
	{
		this.description = description;
	}

	public Patient getPatient()
	{
		return patient;
	}

	public void setPatient(Patient patient)
	{
		this.patient = patient;
	}

	public ArrayList<Radiograph> getRadiographs()
	{
		return radiographs;
	}

	public void setRadiographs(ArrayList<Radiograph> radiographs)
	{
		this.radiographs = radiographs;
	}

	public WorkingTooth getWorkingTooth()
	{
		return workingTooth;
	}

	public void setWorkingTooth(WorkingTooth workingTooth)
	{
		this.workingTooth = workingTooth;
	}
}
