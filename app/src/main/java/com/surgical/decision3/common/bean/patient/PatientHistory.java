package com.surgical.decision3.common.bean.patient;

import org.simpleframework.xml.Attribute;
import org.simpleframework.xml.Element;

public class PatientHistory
{
	@Attribute
	int id;
	@Attribute
	int patientCaseId;
	@Attribute
	int procedureId;
	@Element(required = false)
	String treatmentDate;
	@Element(required = false)
	String medicalHistory;
	
	@Element(required = false)
	String subjectiveSymptom_chiefComplaint;
	
	@Element(required = false)
	String subjectiveSymptom_pain;
	
	@Element(required = false)
	String subjectiveSymptom_historyOfInvolvedTooth;
	
	@Element(required = false)
	String objectiveSymptom_tissueCondition;
	
	@Element(required = false)
	String objectiveSymptom_toothCondition;
	
	@Element(required = false)
	String objectiveSymptom_causeOfPulpInjury;
	
	@Element(required = false)
	String objectiveSymptom_diagnosisTest;
	
	@Element(required = false)
	String radiographicExamination_pulpCavity;
	
	@Element(required = false)
	String radiographicExamination_rootCondition;
	
	@Element(required = false)
	String radiographicExamination_periapicalRegion;
	
	@Element(required = false)
	String radiographicExamination_other;
	
	@Element(required = false)
	String clinicalDiagnosis_pulp;
	
	@Element(required = false)
	String clinicalDiagnosis_periapical;
	
	@Element(required = false)
	String definitiveTreatment;
	
	@Element(required = false)
	String preoperativeTreatment;
	
	@Element(required = false)
	String restorationAfterTreatment;
	
	public int getPatientCaseId()
	{
		return patientCaseId;
	}

	public void setPatientCaseId(int patientCaseId)
	{
		this.patientCaseId = patientCaseId;
	}

	public int getProcedureId()
	{
		return procedureId;
	}

	public void setProcedureId(int procedureId)
	{
		this.procedureId = procedureId;
	}

	public PatientHistory()
	{
		super();
	}

//	public PatientHistory(int id, DateTimeStamp treatmentDate,
//			String treatment, String result)
//	{
//		super();
//		this.id = id;
//		this.treatmentDate = treatmentDate;
//	}

	public int getId()
	{
		return id;
	}

	public void setId(int id)
	{
		this.id = id;
	}

	public String getTreatmentDate()
	{
		return treatmentDate;
	}

	public void setTreatmentDate(String treatmentDate)
	{
		this.treatmentDate = treatmentDate;
	}

	public String getMedicalHistory()
	{
		return medicalHistory;
	}

	public void setMedicalHistory(String medicalHistory)
	{
		this.medicalHistory = medicalHistory;
	}

	public String getSubjectiveSymptom_chiefComplaint()
	{
		return subjectiveSymptom_chiefComplaint;
	}

	public void setSubjectiveSymptom_chiefComplaint(
			String subjectiveSymptom_chiefComplaint)
	{
		this.subjectiveSymptom_chiefComplaint = subjectiveSymptom_chiefComplaint;
	}

	public String getSubjectiveSymptom_pain()
	{
		return subjectiveSymptom_pain;
	}

	public void setSubjectiveSymptom_pain(String subjectiveSymptom_pain)
	{
		this.subjectiveSymptom_pain = subjectiveSymptom_pain;
	}

	public String getSubjectiveSymptom_historyOfInvolvedTooth()
	{
		return subjectiveSymptom_historyOfInvolvedTooth;
	}

	public void setSubjectiveSymptom_historyOfInvolvedTooth(
			String subjectiveSymptom_historyOfInvolvedTooth)
	{
		this.subjectiveSymptom_historyOfInvolvedTooth = subjectiveSymptom_historyOfInvolvedTooth;
	}

	public String getObjectiveSymptom_tissueCondition()
	{
		return objectiveSymptom_tissueCondition;
	}

	public void setObjectiveSymptom_tissueCondition(
			String objectiveSymptom_tissueCondition)
	{
		this.objectiveSymptom_tissueCondition = objectiveSymptom_tissueCondition;
	}

	public String getObjectiveSymptom_toothCondition()
	{
		return objectiveSymptom_toothCondition;
	}

	public void setObjectiveSymptom_toothCondition(
			String objectiveSymptom_toothCondition)
	{
		this.objectiveSymptom_toothCondition = objectiveSymptom_toothCondition;
	}

	public String getObjectiveSymptom_causeOfPulpInjury()
	{
		return objectiveSymptom_causeOfPulpInjury;
	}

	public void setObjectiveSymptom_causeOfPulpInjury(
			String objectiveSymptom_causeOfPulpInjury)
	{
		this.objectiveSymptom_causeOfPulpInjury = objectiveSymptom_causeOfPulpInjury;
	}

	public String getObjectiveSymptom_diagnosisTest()
	{
		return objectiveSymptom_diagnosisTest;
	}

	public void setObjectiveSymptom_diagnosisTest(
			String objectiveSymptom_diagnosisTest)
	{
		this.objectiveSymptom_diagnosisTest = objectiveSymptom_diagnosisTest;
	}

	public String getRadiographicExamination_pulpCavity()
	{
		return radiographicExamination_pulpCavity;
	}

	public void setRadiographicExamination_pulpCavity(
			String radiographicExamination_pulpCavity)
	{
		this.radiographicExamination_pulpCavity = radiographicExamination_pulpCavity;
	}

	public String getRadiographicExamination_rootCondition()
	{
		return radiographicExamination_rootCondition;
	}

	public void setRadiographicExamination_rootCondition(
			String radiographicExamination_rootCondition)
	{
		this.radiographicExamination_rootCondition = radiographicExamination_rootCondition;
	}

	public String getRadiographicExamination_periapicalRegion()
	{
		return radiographicExamination_periapicalRegion;
	}

	public void setRadiographicExamination_periapicalRegion(
			String radiographicExamination_periapicalRegion)
	{
		this.radiographicExamination_periapicalRegion = radiographicExamination_periapicalRegion;
	}

	public String getRadiographicExamination_other()
	{
		return radiographicExamination_other;
	}

	public void setRadiographicExamination_other(
			String radiographicExamination_other)
	{
		this.radiographicExamination_other = radiographicExamination_other;
	}

	public String getClinicalDiagnosis_pulp()
	{
		return clinicalDiagnosis_pulp;
	}

	public void setClinicalDiagnosis_pulp(String clinicalDiagnosis_pulp)
	{
		this.clinicalDiagnosis_pulp = clinicalDiagnosis_pulp;
	}

	public String getClinicalDiagnosis_periapical()
	{
		return clinicalDiagnosis_periapical;
	}

	public void setClinicalDiagnosis_periapical(String clinicalDiagnosis_periapical)
	{
		this.clinicalDiagnosis_periapical = clinicalDiagnosis_periapical;
	}

	public String getDefinitiveTreatment()
	{
		return definitiveTreatment;
	}

	public void setDefinitiveTreatment(String definitiveTreatment)
	{
		this.definitiveTreatment = definitiveTreatment;
	}

	public String getPreoperativeTreatment()
	{
		return preoperativeTreatment;
	}

	public void setPreoperativeTreatment(String preoperativeTreatment)
	{
		this.preoperativeTreatment = preoperativeTreatment;
	}

	public String getRestorationAfterTreatment()
	{
		return restorationAfterTreatment;
	}

	public void setRestorationAfterTreatment(String restorationAfterTreatment)
	{
		this.restorationAfterTreatment = restorationAfterTreatment;
	}

	
}
